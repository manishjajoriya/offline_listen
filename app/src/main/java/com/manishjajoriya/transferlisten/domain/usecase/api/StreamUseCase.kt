package com.manishjajoriya.transferlisten.domain.usecase.api

import com.manishjajoriya.transferlisten.data.remote.MusicApi
import javax.inject.Inject

class StreamUseCase @Inject constructor(private val musicApi: MusicApi) {
  suspend operator fun invoke(trackId: Int, quality: Int): String {
    return musicApi.stream(trackId, quality).url
  }
}
