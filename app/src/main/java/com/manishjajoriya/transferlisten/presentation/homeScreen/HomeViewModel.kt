package com.manishjajoriya.transferlisten.presentation.homeScreen

import android.os.Environment
import android.util.Log
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
  var fetchPlaylistData by mutableStateOf<List<Track>>(emptyList())
    private set

  var streamPlaylistData by mutableStateOf<List<Stream>>(emptyList())
    private set

  var currentFetchIndex by mutableIntStateOf(-1)
    private set

  var currentStremIndex by mutableStateOf(-1)

  var error by mutableStateOf<Exception?>(null)
  var searchLoading by mutableStateOf(false)
  var streamLoading by mutableStateOf(false)

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

  fun streamPlaylist() {
    streamLoading = true
    viewModelScope.launch {
      try {
        val results = mutableListOf<Stream>()
        for ((index, track) in fetchPlaylistData.withIndex()) {
          currentStremIndex++
          val stream =
              runCatching {
                    if (index != 0) delay(1000)
                    Log.e("Log", track.id.toString())
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
      }
    }
  }

  fun reset() {
    fetchPlaylistData = emptyList()
    streamPlaylistData = emptyList()
    currentFetchIndex = -1
    currentStremIndex = -1
    error = null
    searchLoading = false
    streamLoading = false
  }

  fun createDirectory() {
    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
    val letDir = File(path, Constants.APP_DEFAULT_FOLDER_NAME)

    if (!letDir.exists()) {
      letDir.mkdirs()
    }
  }
}
