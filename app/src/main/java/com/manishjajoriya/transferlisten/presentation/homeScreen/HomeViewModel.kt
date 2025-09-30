package com.manishjajoriya.transferlisten.presentation.homeScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.transferlisten.domain.model.Csv
import com.manishjajoriya.transferlisten.domain.model.Track
import com.manishjajoriya.transferlisten.domain.usecase.api.MusicApiUseCase
import com.manishjajoriya.transferlisten.domain.usecase.local.DownloadSongUseCase
import com.manishjajoriya.transferlisten.utils.CsvToList
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val musicApiUseCase: MusicApiUseCase,
    private val downloadSongUseCase: DownloadSongUseCase,
) : ViewModel() {

  var fileName by mutableStateOf("")
  var searchList by mutableStateOf<List<Track?>>(emptyList())
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
    val results = mutableListOf<Track?>()
    viewModelScope.launch {
      for ((index, csv) in csvList.withIndex()) {
        if (index != 0) delay(1000)
        currentSearchIndex++
        val query = "${csv.Track_Name} ${csv.Artist_Name[0]}"
        val track =
            runCatching { musicApiUseCase.searchUseCase(query) }
                .getOrElse {
                  delay(2000)
                  try {
                    musicApiUseCase.searchUseCase(query)
                  } catch (e: Exception) {
                    error = e
                    null
                  }
                }
        results.add(track)
      }
      searchList = results
      searchLoading = false
    }
  }

  suspend fun streamPlaylist() {
    streamLoading = true
    val results = mutableListOf<String?>()
    for ((index, track) in searchList.withIndex()) {
      if (track == null) {
        results.add(null)
        continue
      }
      if (index != 0) delay(1000)
      currentStreamIndex++
      val stream =
          runCatching { musicApiUseCase.streamUseCase(track.id, quality = 5) }
              .getOrElse {
                delay(2000)
                try {
                  musicApiUseCase.streamUseCase(track.id, quality = 5)
                } catch (e: Exception) {
                  error = e
                  null
                }
              }
      results.add(stream)
    }
    streamList = results.toList()
    streamLoading = false
    currentStreamIndex = -1
  }

  fun downloadPlaylist() {
    downloadSongUseCase(fileName, streamList, searchList)
  }

  fun reset() {
    searchList = emptyList()
    streamList = emptyList()
    currentSearchIndex = -1
    currentStreamIndex = -1
    searchLoading = false
    streamLoading = false
    error = null
  }
}
