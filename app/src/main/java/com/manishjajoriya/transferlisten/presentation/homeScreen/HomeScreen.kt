package com.manishjajoriya.transferlisten.presentation.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.transferlisten.R
import com.manishjajoriya.transferlisten.utils.Constants

@Composable
fun HomeScreen(modifier: Modifier) {

  Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
    Spacer(
      Modifier
        .height(Constants.largePadding)
        .fillMaxWidth()
    )
    Text(
      text = "Upload Playlist", style = TextStyle(
        fontSize = Constants.largeFontSize,
        fontWeight = FontWeight.Bold,
        fontFamily = Constants.customFont
      )
    )
    Spacer(Modifier.height(Constants.smallPadding))
    Text(
      text = "Upload a song.csv file to fetch songs.", style = TextStyle(
        fontSize = Constants.smallFontSize,
        fontWeight = FontWeight.Light,
        fontFamily = Constants.customFont
      )
    )
    Spacer(Modifier.height(Constants.mediumPadding))

    Column(
      modifier = Modifier
        .height(120.dp)
        .fillMaxWidth()
        .drawBehind {
          val strokeWidth = 2.dp.toPx()
          val cornerRadius = 20.dp.toPx()
          val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
          drawRoundRect(
            color = Color.LightGray,
            style = Stroke(width = strokeWidth, pathEffect = pathEffect),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius, cornerRadius)
          )
        },
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Image(
        modifier = Modifier.size(36.dp),
        painter = painterResource(R.drawable.outlined_cloud_upload),
        contentDescription = "upload"
      )
      Text(
        text = "Click to upload csv file",
        style = TextStyle(
          fontSize = Constants.mediumFontSize,
          fontWeight = FontWeight.Normal,
          fontFamily = Constants.customFont
        )
      )
    }

  }
}
