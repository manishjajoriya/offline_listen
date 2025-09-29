package com.manishjajoriya.transferlisten.presentation.homeScreen

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manishjajoriya.transferlisten.R
import com.manishjajoriya.transferlisten.presentation.homeScreen.component.LeftPlaylistItem
import com.manishjajoriya.transferlisten.presentation.homeScreen.component.RightPlaylistItem
import com.manishjajoriya.transferlisten.ui.theme.Pink
import com.manishjajoriya.transferlisten.utils.Constants
import com.manishjajoriya.transferlisten.utils.CsvToList
import com.manishjajoriya.transferlisten.utils.VolatileData
import java.time.LocalDateTime

@Composable
fun HomeScreen(modifier: Modifier) {

  val context: Context = LocalContext.current
  val homeViewModel: HomeViewModel = viewModel()
  var csvList by remember { mutableStateOf(VolatileData.csvData) }
  var fileUri by remember { mutableStateOf<Uri?>(null) }
  var fileName by remember { mutableStateOf<String?>(null) }

  val launcher =
      rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        it?.let { uri ->
          fileUri = uri
          fileName = getFileNameFromUri(uri, context)
          homeViewModel.fileName = fileName.toString()
          val data = CsvToList(context, uri)
          VolatileData.csvData = data
          csvList = data
          homeViewModel.reset()
          homeViewModel.searchPlaylist(csvList)
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
                      launcher.launch(arrayOf("text/comma-separated-values"))
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
          text = if (fileUri != null) "file uploaded : $fileName" else "Click to upload csv file",
          style =
              TextStyle(
                  fontSize = Constants.mediumFontSize,
                  fontWeight = FontWeight.Normal,
                  fontFamily = Constants.customFont,
              ),
      )
    }
    Spacer(Modifier.height(Constants.mediumPadding))
    Box(modifier = Modifier.fillMaxSize()) {
      if (homeViewModel.searchLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          CircularProgressIndicator(
              modifier = Modifier.size(48.dp),
              color = Pink,
              trackColor = Color.White,
          )
          Text(
              text = "${homeViewModel.currentFetchIndex + 1}/${csvList.size}",
          )
        }
      } else if (homeViewModel.streamLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          CircularProgressIndicator(
              modifier = Modifier.size(48.dp),
              color = Pink,
              trackColor = Color.White,
          )
          Text(
              text = "${homeViewModel.currentStreamIndex + 1}/${csvList.size}",
          )
        }
      } else if (csvList.isNotEmpty() && homeViewModel.fetchPlaylistData.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Constants.mediumPadding),
        ) {
          items(csvList.size) { index ->
            Row {
              LeftPlaylistItem(csvList[index], Modifier.weight(.45f))
              Spacer(Modifier.width(Constants.smallPadding))
              RightPlaylistItem(homeViewModel.fetchPlaylistData[index], Modifier.weight(.45f))
            }
          }
        }
      }
    }
  }
}

fun getFileNameFromUri(fileUri: Uri, context: Context): String {
  val cursor = context.contentResolver.query(fileUri, null, null, null, null, null)
  cursor?.use { it ->
    if (it.moveToFirst()) {
      val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
      if (index != -1) {
        return it.getString(index)
      }
    }
  }
  return "MyPlaylist_${LocalDateTime.now()}"
}
