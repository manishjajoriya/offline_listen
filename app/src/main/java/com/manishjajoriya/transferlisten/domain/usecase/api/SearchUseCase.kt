package com.manishjajoriya.transferlisten.domain.usecase.api

import android.util.Log
import com.manishjajoriya.transferlisten.data.remote.MusicApi
import com.manishjajoriya.transferlisten.domain.model.search.Item
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val musicApi: MusicApi) {
  suspend operator fun invoke(searchTerm: String): Item {
    return musicApi.search(searchTerm).items[0]
  }
}
