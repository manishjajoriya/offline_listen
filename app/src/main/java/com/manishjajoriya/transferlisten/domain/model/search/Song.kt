package com.manishjajoriya.transferlisten.domain.model.search

data class Song(
    val items: List<Item>,
    val limit: Int,
    val offset: Int,
    val totalNumberOfItems: Int
)