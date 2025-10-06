package com.manishjajoriya.transferlisten.domain.model.stream

import com.google.gson.annotations.SerializedName

data class TrackData(
  val trackInfo: TrackInfo? = null,
  val streamInfo: StreamInfo? = null,
  val originalTrackUrl: OriginalTrackUrl? = null
)

data class TrackInfo(
  val id: Long,
  val title: String,
  val duration: Int,
  val replayGain: Double?,
  val peak: Double?,
  val allowStreaming: Boolean,
  val streamReady: Boolean,
  val payToStream: Boolean,
  val adSupportedStreamReady: Boolean,
  val djReady: Boolean,
  val stemReady: Boolean,
  val streamStartDate: String?,
  val premiumStreamingOnly: Boolean,
  val trackNumber: Int?,
  val volumeNumber: Int?,
  val version: String?,
  val popularity: Int?,
  val copyright: String?,
  val bpm: Int?,
  val url: String?,
  val isrc: String?,
  val editable: Boolean,
  val explicit: Boolean,
  val audioQuality: String?,
  val audioModes: List<String>?,
  val mediaMetadata: MediaMetadata?,
  val upload: Boolean,
  val accessType: String?,
  val spotlighted: Boolean,
  val artist: Artist?,
  val artists: List<Artist>?,
  val album: Album?,
  val mixes: Map<String, Any>?
)

data class MediaMetadata(
  val tags: List<String>?
)

data class Artist(
  val id: Long,
  val name: String?,
  val handle: String?,
  val type: String?,
  val picture: String?
)

data class Album(
  val id: Long,
  val title: String?,
  val cover: String?,
  val vibrantColor: String?,
  val videoCover: String?
)

data class StreamInfo(
  val trackId: Long,
  val assetPresentation: String?,
  val audioMode: String?,
  val audioQuality: String?,
  val manifestMimeType: String?,
  val manifestHash: String?,
  val manifest: String?,
  val albumReplayGain: Double?,
  val albumPeakAmplitude: Double?,
  val trackReplayGain: Double?,
  val trackPeakAmplitude: Double?,
  val bitDepth: Int?,
  val sampleRate: Int?
)

data class OriginalTrackUrl(
  @SerializedName("OriginalTrackUrl") val originalTrackUrl: String
)

