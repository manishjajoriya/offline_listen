package com.manishjajoriya.transferlisten.data.remote

import com.manishjajoriya.transferlisten.domain.model.search.Song
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

  @GET("search/") suspend fun search(@Query("s") searchTerm: String): Song

  @GET("track/")
  suspend fun stream(@Query("id") id: Int, @Query("quality") quality: String): List<Any>
}
