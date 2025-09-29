package com.manishjajoriya.transferlisten.presentation.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.transferlisten.domain.model.Csv
import com.manishjajoriya.transferlisten.domain.model.Track
import com.manishjajoriya.transferlisten.domain.usecase.MusicApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val musicApiUseCase: MusicApiUseCase) :
    ViewModel() {
  var fetchPlaylistData by mutableStateOf<List<Track>>(emptyList())
    private set

  var currentIndex by mutableIntStateOf(-1)
    private set

  var error by mutableStateOf<Exception?>(null)
  var loading by mutableStateOf(false)

  fun searchPlaylist(csvList: List<Csv>) {
    currentIndex = -1
    loading = true
    error = null
    viewModelScope.launch {
      try {
        val results = mutableListOf<Track>()
        for ((index, csv) in csvList.withIndex()) {
          currentIndex = index
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
        loading = false
      }
    }
  }

  fun reset() {
    fetchPlaylistData = emptyList()
    currentIndex = -1
    error = null
    loading = false
  }
}
