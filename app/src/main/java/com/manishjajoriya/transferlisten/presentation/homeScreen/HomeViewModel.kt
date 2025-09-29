package com.manishjajoriya.transferlisten.presentation.homeScreen

import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketch.Ketch
import com.manishjajoriya.transferlisten.domain.model.Csv
import com.manishjajoriya.transferlisten.domain.model.Stream
import com.manishjajoriya.transferlisten.domain.model.Track
import com.manishjajoriya.transferlisten.domain.usecase.MusicApiUseCase
import com.manishjajoriya.transferlisten.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.io.File
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val musicApiUseCase: MusicApiUseCase, private val ketch: Ketch) : ViewModel() {

  var fileName by mutableStateOf("")
  var fetchPlaylistData by mutableStateOf<List<Track>>(emptyList())
    private set

  var streamPlaylistData by mutableStateOf<List<Stream>>(emptyList())
    private set

  var currentFetchIndex by mutableIntStateOf(-1)
    private set

  var currentStreamIndex by mutableIntStateOf(-1)
    private set

  var currentDownloadIndex by mutableIntStateOf(-1)
    private set

  var error by mutableStateOf<Exception?>(null)
    private set

  var searchLoading by mutableStateOf(false)
    private set

  var streamLoading by mutableStateOf(false)
    private set

  var downloadLoading by mutableStateOf(false)
    private set

  fun searchPlaylist(csvList: List<Csv>) {
    searchLoading = true
    viewModelScope.launch {
      try {
        val results = mutableListOf<Track>()
        for ((index, csv) in csvList.withIndex()) {
          currentFetchIndex++
          val query = "${csv.Track_Name} ${csv.Artist_Name[0]}"
          val track =
              runCatching {
                    if (index != 0) delay(1000)
                    musicApiUseCase.searchUseCase(query)
                  }
                  .getOrElse {
                    delay(1000)
                    musicApiUseCase.searchUseCase(query)
                  }
          results.add(track)
        }
        fetchPlaylistData = results.toList()
      } catch (e: Exception) {
        error = e
      } finally {
        searchLoading = false
      }
    }
  }

  suspend fun streamPlaylist() {
    streamLoading = true
    try {
      val results = mutableListOf<Stream>()
      for ((index, track) in fetchPlaylistData.withIndex()) {
        currentStreamIndex++
        val stream =
            runCatching {
                  if (index != 0) delay(1000)
                  musicApiUseCase.streamUseCase(track.id, quality = 5)
                }
                .getOrElse {
                  delay(1000)
                  musicApiUseCase.streamUseCase(track.id, quality = 5)
                }
        results.add(stream)
      }
      streamPlaylistData = results.toList()
    } catch (e: Exception) {
      error = e
    } finally {
      streamLoading = false
      currentStreamIndex = -1
    }
  }

  fun reset() {
    fetchPlaylistData = emptyList()
    streamPlaylistData = emptyList()
    currentFetchIndex = -1
    currentStreamIndex = -1
    currentDownloadIndex = -1
    error = null
    searchLoading = false
    streamLoading = false
    downloadLoading = false
  }

  fun createDirectory(
      parentDirectoryName: String =
          Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path,
      childDirectoryName: String,
  ): String {
    val letDir = File(parentDirectoryName, childDirectoryName)

    if (!letDir.exists()) {
      letDir.mkdirs()
    }
    return letDir.path
  }

  fun downloadSongFromStreamPlaylist() {
    downloadLoading = true
    val playlistName = File(fileName).nameWithoutExtension
    val downloadPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path +
            "/${Constants.APP_DEFAULT_FOLDER_NAME}"

    val playlistPath =
        createDirectory(parentDirectoryName = downloadPath, childDirectoryName = playlistName)
    try {
      for ((index, stream) in streamPlaylistData.withIndex()) {
        currentDownloadIndex++
        ketch.download(
            tag = "audio",
            url = stream.url,
            fileName = "${fetchPlaylistData[index].isrc}.mp3",
            path = playlistPath,
        )
      }
    } catch (e: Exception) {
      error = e
    } finally {
      downloadLoading = false
      currentDownloadIndex = -1
    }
  }
}
