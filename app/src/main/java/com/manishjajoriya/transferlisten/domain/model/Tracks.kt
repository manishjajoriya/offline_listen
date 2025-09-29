package com.manishjajoriya.transferlisten.domain.model

data class Tracks(
  val pagination: Pagination,
  val tracks: List<Track>
)