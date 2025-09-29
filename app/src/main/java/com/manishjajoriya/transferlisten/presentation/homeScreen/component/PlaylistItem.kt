package com.manishjajoriya.transferlisten.presentation.homeScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.manishjajoriya.transferlisten.domain.model.Csv
import com.manishjajoriya.transferlisten.ui.theme.Gray
import com.manishjajoriya.transferlisten.utils.Constants

@Composable
fun LeftPlaylistItem(data: Csv, modifier: Modifier = Modifier) {
  Column(
      modifier =
          modifier
              .fillMaxWidth()
              .background(Gray, RoundedCornerShape(8.dp))
              .padding(horizontal = Constants.smallPadding, vertical = Constants.mediumPadding),
      verticalArrangement = Arrangement.spacedBy(4.dp),
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
    Text(
        text = data.Artist_Name.toString(),
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
