package com.manishjajoriya.transferlisten.domain.usecase

import com.manishjajoriya.transferlisten.data.remote.MusicApi
import com.manishjajoriya.transferlisten.domain.model.Track
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val musicApi: MusicApi) {
  suspend operator fun invoke(searchTerm: String): Track {
    return musicApi.search(searchTerm).tracks[0]
  }
}
