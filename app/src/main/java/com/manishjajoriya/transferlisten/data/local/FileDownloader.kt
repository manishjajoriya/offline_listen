package com.manishjajoriya.transferlisten.data.local

import android.content.Context
import android.os.Environment
import android.util.Log
import com.ketch.Ketch
import com.ketch.Status
import com.manishjajoriya.transferlisten.domain.model.Track
import com.manishjajoriya.transferlisten.utils.Constants
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class FileDownloader(private val context: Context, private val ketch: Ketch) {

  suspend fun downloadFileToPrivate(url: String?, track: Track?): File? {
    if (url == null || track == null) return null

    val privateDir = File(context.filesDir, "downloads").apply { if (!exists()) mkdirs() }
    val privateFile = File(privateDir, "${track.title}.mp3")

    // Start download
    val id =
        ketch.download(
            tag = "audio",
            url = url,
            fileName = privateFile.name,
            path = privateDir.absolutePath,
        )

    // Wait until status is SUCCESS, FAILED, or CANCELLED
    val result =
        ketch
            .observeDownloadById(id)
            .filter { event ->
              event?.status == Status.SUCCESS ||
                  event?.status == Status.FAILED ||
                  event?.status == Status.CANCELLED
            }
            .first()

    return when (result?.status) {
      Status.SUCCESS -> privateFile
      Status.FAILED,
      Status.CANCELLED -> null
      else -> null
    }
  }

  suspend fun moveToPublicDownloads(privateFile: File, publicDir: File): File? =
      withContext(Dispatchers.IO) {
        if (!privateFile.exists()) return@withContext null

        val destFile = File(publicDir, privateFile.name)

        try {
          FileInputStream(privateFile).use { input ->
            FileOutputStream(destFile).use { output -> input.copyTo(output) }
          }

          val isDeleted = privateFile.delete()
          Log.i("AppStorage", "${privateFile.path} is $isDeleted")

          return@withContext destFile
        } catch (e: IOException) {
          e.printStackTrace()
          destFile.delete()
          return@withContext null
        }
      }

  fun createPublicDirectory(fileName: String): File {
    val fileNameWithoutExtension = File(fileName).nameWithoutExtension
    val publicAppDirectory =
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            Constants.APP_DEFAULT_FOLDER_NAME,
        )

    publicAppDirectory.mkdirs()
    val publicPlaylistDirectory = File(publicAppDirectory, fileNameWithoutExtension)
    publicPlaylistDirectory.mkdirs()

    return publicPlaylistDirectory
  }

  fun logAppPrivateStorage(isInitial: Boolean = false) {
    Log.d("AppStorage", "---- App Private Storage ${if(isInitial) "Initial" else ""} ----")

    // Log filesDir
    logDirectoryContents(File(context.filesDir.absolutePath), "filesDir")

    // Log cacheDir
    logDirectoryContents(File(context.cacheDir.absolutePath), "cacheDir")

    // Log external filesDir (if available)
    context.getExternalFilesDir(null)?.let { logDirectoryContents(it, "externalFilesDir") }

    Log.d("AppStorage", "----------------------------")
  }

  private fun logDirectoryContents(dir: File, tag: String, indent: String = "") {
    if (!dir.exists()) {
      Log.d("AppStorage", "$indent[$tag] Directory does not exist: ${dir.absolutePath}")
      return
    }

    Log.d("AppStorage", "$indent[$tag] ${dir.absolutePath}")
    dir.listFiles()?.forEach { file ->
      if (file.isDirectory) {
        logDirectoryContents(file, file.name, "$indent   ")
      } else {
        Log.d("AppStorage", "$indent   - ${file.name} (size=${file.length()} bytes)")
      }
    }
  }
}
