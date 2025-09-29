package com.manishjajoriya.transferlisten.domain.usecase

import com.manishjajoriya.transferlisten.data.remote.MusicApi
import com.manishjajoriya.transferlisten.domain.model.Stream
import javax.inject.Inject

class StreamUseCase @Inject constructor(private val musicApi: MusicApi) {
  suspend operator fun invoke(trackId: Int, quality: Int): Stream {
    return musicApi.stream(trackId, quality)
  }
}
