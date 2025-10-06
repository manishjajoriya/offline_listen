package com.manishjajoriya.transferlisten.utils

import android.content.Context
import android.net.Uri
import com.manishjajoriya.transferlisten.domain.model.csv.Csv
import java.io.BufferedReader

object CsvToList {

  operator fun invoke(context: Context, uri: Uri): List<Csv> {
    val csvContent = readCsvFromUri(context, uri)
    if (csvContent.isEmpty()) return emptyList()

    val lines = csvContent.trim().lines()

    // First row = headers
    val headers = parseCSVLine(lines[0]).map { it.replace("\\s+".toRegex(), "_") }

    return lines.drop(1).mapNotNull { line ->
      val values = parseCSVLine(line)
      try {
        val map = headers.zip(values).toMap()

        Csv(
            Track_URI = map["Track_URI"] ?: "",
            Track_Name = map["Track_Name"] ?: "",
            Album_Name = map["Album_Name"] ?: "",
            Artist_Name = map["Artist_Name(s)"]?.split(";")?.map { it.trim() } ?: emptyList(),
            Release_Date = map["Release_Date"] ?: "",
            Duration = map["Duration_(ms)"]?.toIntOrNull() ?: 0,
            Popularity = map["Popularity"]?.toIntOrNull() ?: 0,
            Explicit = map["Explicit"]?.toBoolean() ?: false,
            Added_By = map["Added_By"] ?: "",
            Added_At = map["Added_At"] ?: "",
            Genres = map["Genres"]?.split(",")?.map { it.trim() } ?: emptyList(),
            Record_Label = map["Record_Label"] ?: "",
            Danceability = map["Danceability"]?.toDoubleOrNull() ?: 0.0,
            Energy = map["Energy"]?.toDoubleOrNull() ?: 0.0,
            Key = map["Key"]?.toIntOrNull() ?: 0,
            Loudness = map["Loudness"]?.toDoubleOrNull() ?: 0.0,
            Mode = map["Mode"]?.toIntOrNull() ?: 0,
            Speechiness = map["Speechiness"]?.toDoubleOrNull() ?: 0.0,
            acousticness = map["Acousticness"]?.toDoubleOrNull() ?: 0.0,
            Instrumentalness = map["Instrumentalness"]?.toIntOrNull() ?: 0,
            Liveness = map["Liveness"]?.toDoubleOrNull() ?: 0.0,
            Valence = map["Valence"]?.toDoubleOrNull() ?: 0.0,
            Tempo = map["Tempo"]?.toDoubleOrNull() ?: 0.0,
            Time_Signature = map["Time_Signature"]?.toIntOrNull() ?: 0,
        )
      } catch (e: Exception) {
        e.printStackTrace()
        null
      }
    }
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
