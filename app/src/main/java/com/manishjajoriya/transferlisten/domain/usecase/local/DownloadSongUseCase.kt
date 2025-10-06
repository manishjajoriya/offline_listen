package com.manishjajoriya.transferlisten.domain.usecase.local

import com.manishjajoriya.transferlisten.data.local.FileDownloader
import com.manishjajoriya.transferlisten.domain.model.search.Item
import jakarta.inject.Inject
import java.io.File

class DownloadSongUseCase @Inject constructor(private val fileDownloader: FileDownloader) {

  fun createPublicDirectory(fileName: String): File {
    return fileDownloader.createPublicDirectory(fileName)
  }

  suspend fun downloadFileToPrivate(url: String?, item : Item): File? {
    return fileDownloader.downloadFileToPrivate(url, item)
  }

  suspend fun moveToPublicDownloads(privateFile: File, publicDir: File): File? {
    return fileDownloader.moveToPublicDownloads(privateFile, publicDir)
  }

  fun logAppPrivateStorage() {
    fileDownloader.logAppPrivateStorage()
  }
}
