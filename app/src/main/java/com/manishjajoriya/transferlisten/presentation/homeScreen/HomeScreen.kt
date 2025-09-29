package com.manishjajoriya.transferlisten.presentation.homeScreen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.transferlisten.R
import com.manishjajoriya.transferlisten.presentation.homeScreen.component.LeftPlaylistItem
import com.manishjajoriya.transferlisten.utils.Constants
import com.manishjajoriya.transferlisten.utils.CsvToList
import com.manishjajoriya.transferlisten.utils.VolatileData

@Composable
fun HomeScreen(modifier: Modifier) {

  val context: Context = LocalContext.current
  var csvData by remember { mutableStateOf(VolatileData.csvData) }
  var result by remember { mutableStateOf<Uri?>(null) }
  val launcher =
      rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result = it
        it?.let { uri ->
          val data = CsvToList(context, it)
          VolatileData.csvData = data
          csvData = data
          println("Parsed CSV: ${VolatileData.csvData[0]}")
        }
      }

  Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
    Spacer(Modifier.height(Constants.largePadding).fillMaxWidth())
    Text(
        text = "Upload Playlist",
        style =
            TextStyle(
                fontSize = Constants.largeFontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = Constants.customFont,
            ),
    )
    Spacer(Modifier.height(Constants.smallPadding))
    Text(
        text = "Upload a song.csv file to fetch songs.",
        style =
            TextStyle(
                fontSize = Constants.smallFontSize,
                fontWeight = FontWeight.Light,
                fontFamily = Constants.customFont,
                color = Color.Gray,
            ),
    )
    Spacer(Modifier.height(Constants.mediumPadding))

    Column(
        modifier =
            Modifier.height(120.dp)
                .fillMaxWidth()
                .drawBehind {
                  val strokeWidth = 2.dp.toPx()
                  val cornerRadius = 20.dp.toPx()
                  val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                  drawRoundRect(
                      color = Color.LightGray,
                      style = Stroke(width = strokeWidth, pathEffect = pathEffect),
                      cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                  )
                }
                .clickable(
                    onClick = {
                      launcher.launch(arrayOf("*/*"))
                      println("log $result")
                      Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
      Image(
          modifier = Modifier.size(36.dp),
          painter = painterResource(R.drawable.outlined_cloud_upload),
          contentDescription = "upload",
      )
      Text(
          text = if (result != null) "file is uploaded" else "Click to upload csv file",
          style =
              TextStyle(
                  fontSize = Constants.mediumFontSize,
                  fontWeight = FontWeight.Normal,
                  fontFamily = Constants.customFont,
              ),
      )
    }
    LazyColumn {
      items(csvData.size) { index ->
        LeftPlaylistItem(csvData[index], Modifier.fillMaxWidth(.5f))
        Spacer(Modifier.height(Constants.smallPadding))
      }
    }
  }
}
