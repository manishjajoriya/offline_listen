package com.manishjajoriya.transferlisten.data.remote

import com.manishjajoriya.transferlisten.domain.model.Stream
import com.manishjajoriya.transferlisten.domain.model.Tracks
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

  @GET("search") suspend fun search(@Query("q") searchTerm: String): Tracks

  @GET("stream")
  suspend fun stream(@Query("trackId") trackId: Int, @Query("quality") quality: Int = 5): Stream
}
