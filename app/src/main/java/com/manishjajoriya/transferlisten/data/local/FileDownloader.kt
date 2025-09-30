package com.manishjajoriya.transferlisten.data.local

import android.os.Environment
import com.ketch.Ketch
import com.manishjajoriya.transferlisten.domain.model.Track
import com.manishjajoriya.transferlisten.utils.Constants
import java.io.File

class FileDownloader(private val ketch: Ketch) {
  fun downloadFiles(folderName: String, urlList: List<String?>, searchList: List<Track?>) {

    val folderNameWithoutExtension = File(folderName).nameWithoutExtension
    val downloadFolderPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path +
            "/${Constants.APP_DEFAULT_FOLDER_NAME}"

    val finalDownloadLocation =
        createDirectory(
            parentDirectoryName = downloadFolderPath,
            childDirectoryName = folderNameWithoutExtension,
        )

    for ((index, url) in urlList.withIndex()) {
      val track = searchList[index]

      if (url != null && track != null) {
        ketch.download(
            tag = "audio",
            url = url,
            fileName = "${track.title}.mp3",
            path = finalDownloadLocation,
        )
      }
    }
  }

  fun createDirectory(
      parentDirectoryName: String =
          Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path,
      childDirectoryName: String,
  ): String {
    val letDir = File(parentDirectoryName, childDirectoryName)

    if (!letDir.exists()) {
      letDir.mkdirs()
    }
    return letDir.path
  }
}
