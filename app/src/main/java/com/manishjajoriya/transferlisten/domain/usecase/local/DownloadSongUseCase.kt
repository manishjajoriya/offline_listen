package com.manishjajoriya.transferlisten.domain.usecase.local

import com.manishjajoriya.transferlisten.data.local.FileDownloader
import com.manishjajoriya.transferlisten.domain.model.Track
import jakarta.inject.Inject

class DownloadSongUseCase @Inject constructor(private val fileDownloader: FileDownloader) {
  operator fun invoke(fileName: String, urlList: List<String?>, searchList: List<Track?>) {
    fileDownloader.downloadFiles(fileName, urlList, searchList)
  }
}
