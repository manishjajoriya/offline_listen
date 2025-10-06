package com.manishjajoriya.transferlisten.presentation.homeScreen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.transferlisten.domain.model.csv.Csv
import com.manishjajoriya.transferlisten.domain.model.search.Item
import com.manishjajoriya.transferlisten.domain.usecase.api.MusicApiUseCase
import com.manishjajoriya.transferlisten.domain.usecase.local.DownloadSongUseCase
import com.manishjajoriya.transferlisten.utils.CsvToList
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val musicApiUseCase: MusicApiUseCase,
    private val downloadSongUseCase: DownloadSongUseCase,
) : ViewModel() {

  var fileName by mutableStateOf("")
  var searchList by mutableStateOf<List<Item?>>(emptyList())
    private set

  var streamList by mutableStateOf<List<String?>>(emptyList())
    private set

  var currentSearchIndex by mutableIntStateOf(-1)
    private set

  var currentStreamIndex by mutableIntStateOf(-1)
    private set

  var searchLoading by mutableStateOf(false)
    private set

  var streamLoading by mutableStateOf(false)
    private set

  var csvList by mutableStateOf<List<Csv>>(emptyList())
    private set

  var error by mutableStateOf<Exception?>(null)
    private set

  fun csvToListConverter(context: Context, uri: Uri) {
    csvList = CsvToList(context, uri)
  }

  fun searchPlaylist() {
    searchLoading = true
    val results = mutableListOf<Item?>()
    viewModelScope.launch {
      for ((index, csv) in csvList.withIndex()) {
        if (index != 0) delay(1000)
        currentSearchIndex++
        val query = "${csv.Track_Name} ${csv.Artist_Name.firstOrNull() ?: ""}"
        val track =
            try {
              musicApiUseCase.searchUseCase(query)
            } catch (e: HttpException) {
              Log.e("ERROR", "HTTP ${e.code()} for query: $query", e)
              null
            } catch (e: Exception) {
              Log.e("ERROR", "Other error for query: $query", e)
              null
            }
        results.add(track)
      }
      searchList = results
      searchLoading = false
    }
  }

  fun streamPlaylist() {
    viewModelScope.launch {
      streamLoading = true
      val publicDir = downloadSongUseCase.createPublicDirectory(fileName)
      val results = mutableListOf<String?>()

      for ((index, track) in searchList.withIndex()) {
        if (track == null) {
          results.add(null)
          continue
        }
        if (index != 0) delay(1000)
        currentStreamIndex++
        val trackData =
            try {
              musicApiUseCase.streamUseCase(track.id)
            } catch (e: Exception) {
              Log.e("LOG", "First attempt failed for track ${track.id}", e)
              delay(2000)
              try {
                musicApiUseCase.streamUseCase(track.id)
              } catch (e2: Exception) {
                Log.e("LOG", "Second attempt failed for track ${track.id}", e2)
                error = e2
                null
              }
            }

        val privateFile =
            downloadSongUseCase.downloadFileToPrivate(
                trackData?.originalTrackUrl?.originalTrackUrl,
                track,
            )
        privateFile?.let { downloadSongUseCase.moveToPublicDownloads(it, publicDir) }
        results.add(trackData?.originalTrackUrl?.originalTrackUrl)
      }
      streamList = results.toList()
      streamLoading = false
      currentStreamIndex = -1
    }
  }

  fun reset() {
    csvList = emptyList()
    searchList = emptyList()
    streamList = emptyList()
    currentSearchIndex = -1
    currentStreamIndex = -1
    searchLoading = false
    streamLoading = false
    error = null
  }
}
