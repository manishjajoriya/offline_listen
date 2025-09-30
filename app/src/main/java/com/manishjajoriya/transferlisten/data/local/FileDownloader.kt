package com.manishjajoriya.transferlisten.data.local

import android.os.Environment
import com.ketch.Ketch
import com.manishjajoriya.transferlisten.domain.model.Track
import com.manishjajoriya.transferlisten.utils.Constants
import java.io.File

class FileDownloader(private val ketch: Ketch) {
  fun downloadFiles(fileName: String, urlList: List<String?>, searchList: List<Track?>) {

    val fileNameWithoutExtension = File(fileName).nameWithoutExtension
    val publicAppDirectory =
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            Constants.APP_DEFAULT_FOLDER_NAME,
        )

    if (!publicAppDirectory.exists()) publicAppDirectory.mkdirs()
    val publicPlaylistDirectory = File(publicAppDirectory, fileNameWithoutExtension)
    if (!publicPlaylistDirectory.exists()) publicPlaylistDirectory.mkdirs()

    for ((index, url) in urlList.withIndex()) {
      val track = searchList[index]

      if (url != null && track != null) {
        ketch.download(
            tag = "audio",
            url = url,
            fileName = "${track.title}.mp3",
            path = publicPlaylistDirectory.absolutePath,
        )
      }
    }
  }
}
