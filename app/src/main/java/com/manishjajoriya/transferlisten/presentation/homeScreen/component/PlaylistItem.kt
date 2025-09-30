package com.manishjajoriya.transferlisten.presentation.homeScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.transferlisten.R
import com.manishjajoriya.transferlisten.domain.model.Csv
import com.manishjajoriya.transferlisten.domain.model.Track
import com.manishjajoriya.transferlisten.ui.theme.Gray
import com.manishjajoriya.transferlisten.utils.Constants

@Composable
fun LeftPlaylistItem(data: Csv, modifier: Modifier = Modifier) {
  Column(
      modifier =
          modifier
              .height(68.dp)
              .background(Gray, RoundedCornerShape(8.dp))
              .padding(horizontal = Constants.smallPadding, vertical = Constants.mediumPadding),
      verticalArrangement = Arrangement.Center,
  ) {
    Text(
        text = data.Track_Name,
        style =
            TextStyle(
                fontSize = Constants.smallFontSize,
                color = Color.White,
                fontFamily = Constants.customFont,
            ),
        maxLines = 1,
    )
    Spacer(Modifier.height(Constants.extraSmallPadding))
    Text(
        text = data.Artist_Name[0],
        style =
            TextStyle(
                fontSize = Constants.extraSmallFontSize,
                color = Color(0xFF9ca3af),
                fontFamily = Constants.customFont,
            ),
        maxLines = 1,
    )
  }
}

@Composable
fun RightPlaylistItem(track: Track?, modifier: Modifier = Modifier) {
  Row(
      modifier =
          modifier
              .height(68.dp)
              .background(Gray, RoundedCornerShape(8.dp))
              .padding(horizontal = Constants.smallPadding, vertical = Constants.mediumPadding),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    if (track != null) {
      AsyncImage(
          modifier = Modifier.size(52.dp),
          model = track.images.small,
          contentDescription = "${track.title} image",
      )
    } else {
      Image(
          painterResource(R.drawable.no_image_svgrepo_com),
          contentDescription = "No image",
          modifier = Modifier.size(52.dp),
      )
    }

    Column(
        modifier = Modifier.padding(start = Constants.extraSmallPadding),
        verticalArrangement = Arrangement.Center,
    ) {
      Text(
          text = track?.title ?: "No title",
          style =
              TextStyle(
                  fontSize = Constants.smallFontSize,
                  color = Color.White,
                  fontFamily = Constants.customFont,
              ),
          maxLines = 1,
      )
      Spacer(Modifier.height(Constants.extraSmallPadding))
      Text(
          text = track?.artist ?: "No artist",
          style =
              TextStyle(
                  fontSize = Constants.extraSmallFontSize,
                  color = Color(0xFF9ca3af),
                  fontFamily = Constants.customFont,
              ),
          maxLines = 1,
      )
    }
  }
}
