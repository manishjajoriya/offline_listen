package com.manishjajoriya.transferlisten.utils

import android.content.Context
import android.net.Uri
import java.io.BufferedReader

object CsvToList {

  operator fun invoke(context: Context, uri: Uri): List<Map<String, Any?>> {
    val csvContent = readCsvFromUri(context, uri)
    if (csvContent.isEmpty()) return emptyList()

    val lines = csvContent.trim().lines()

    // First row = headers
    val headers = parseCSVLine(lines[0]).map { it.replace("\\s+".toRegex(), "_") }

    // Remaining rows = data
    val jsonData =
        lines.drop(1).map { line ->
          val values = parseCSVLine(line).toMutableList()
          val obj = mutableMapOf<String, Any?>()

          headers.forEachIndexed { index, header ->
            var key = header
            var value: Any? = values.getOrNull(index)

            if (key == "Artist_Name(s)" && value is String) {
              value = value.split(";").map { it.trim() }
              key = "Artist_Name"
            }

            obj[key] = value
          }

          obj
        }

    return jsonData
  }

  private fun readCsvFromUri(context: Context, uri: Uri): String {
    return try {
      context.contentResolver.openInputStream(uri)?.bufferedReader()?.use(BufferedReader::readText)
          ?: ""
    } catch (e: Exception) {
      e.printStackTrace()
      ""
    }
  }

  private fun parseCSVLine(line: String): List<String> {
    val result = mutableListOf<String>()
    var current = StringBuilder()
    var insideQuotes = false
    var i = 0

    while (i < line.length) {
      val char = line[i]
      when {
        char == '"' && i + 1 < line.length && line[i + 1] == '"' -> {
          current.append('"')
          i++
        }
        char == '"' -> insideQuotes = !insideQuotes
        char == ',' && !insideQuotes -> {
          result.add(current.toString().trim())
          current = StringBuilder()
        }
        else -> current.append(char)
      }
      i++
    }
    result.add(current.toString().trim())
    return result
  }
}
