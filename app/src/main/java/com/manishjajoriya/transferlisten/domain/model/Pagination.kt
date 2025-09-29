package com.manishjajoriya.transferlisten.domain.model

data class Pagination(
  val hasMore: Boolean,
  val limit: Int,
  val offset: Int,
  val returned: Int,
  val total: Int
)