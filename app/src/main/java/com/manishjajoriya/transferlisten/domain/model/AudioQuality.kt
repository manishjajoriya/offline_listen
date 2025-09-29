package com.manishjajoriya.transferlisten.domain.model

data class AudioQuality(
  val isHiRes: Boolean,
  val maximumBitDepth: Int,
  val maximumSamplingRate: Double
)