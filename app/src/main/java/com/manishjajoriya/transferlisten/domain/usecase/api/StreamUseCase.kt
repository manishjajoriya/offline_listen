package com.manishjajoriya.transferlisten.domain.usecase.api

import android.util.Log
import com.google.gson.Gson
import com.manishjajoriya.transferlisten.data.remote.MusicApi
import com.manishjajoriya.transferlisten.domain.model.stream.OriginalTrackUrl
import com.manishjajoriya.transferlisten.domain.model.stream.StreamInfo
import com.manishjajoriya.transferlisten.domain.model.stream.TrackData
import com.manishjajoriya.transferlisten.domain.model.stream.TrackInfo
import javax.inject.Inject

class StreamUseCase @Inject constructor(private val musicApi: MusicApi, private val gson: Gson) {
  suspend operator fun invoke(id: Int, quality: String = "LOSSLESS"): TrackData {
    val response = musicApi.stream(id, "LOSSLESS")
    val trackInfoJson = gson.toJson(response[0])
    val streamInfoJson = gson.toJson(response[1])
    val originalTrackUrlJson = gson.toJson(response[2])

    val trackInfo = gson.fromJson(trackInfoJson, TrackInfo::class.java)
    val streamInfo = gson.fromJson(streamInfoJson, StreamInfo::class.java)
    val originalTrackUrl = gson.fromJson(originalTrackUrlJson, OriginalTrackUrl::class.java)

    return TrackData(
        trackInfo = trackInfo,
        streamInfo = streamInfo,
        originalTrackUrl = originalTrackUrl,
    )
  }
}
