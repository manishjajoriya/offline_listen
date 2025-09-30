package com.manishjajoriya.transferlisten.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manishjajoriya.transferlisten.R

object Constants {

  const val BASE_URL = "https://dab.yeet.su/api/"

  // Padding
  val extraSmallPadding = 4.dp
  val smallPadding = 8.dp
  val mediumPadding = 12.dp
  val largePadding = 16.dp
  val extraLargePadding = 24.dp

  // Font size
  val extraSmallFontSize = 12.sp
  val smallFontSize = 16.sp
  val mediumFontSize = 20.sp
  val largeFontSize = 24.sp
  val extraLargeFontSize = 28.sp
  val displayFontSize = 32.sp

  // Custom font
  val customFont =
      FontFamily(
          Font(R.font.roboto_semi_condensed_thin, FontWeight.Thin),
          Font(R.font.roboto_semi_condensed_extralight, FontWeight.ExtraLight),
          Font(R.font.roboto_semi_condensed_light, FontWeight.Light),
          Font(R.font.roboto_semi_condensed_regular, FontWeight.Normal),
          Font(R.font.roboto_semi_condensed_medium, FontWeight.Medium),
          Font(R.font.roboto_semi_condensed_semibold, FontWeight.SemiBold),
          Font(R.font.roboto_semi_condensed_bold, FontWeight.Bold),
          Font(R.font.roboto_semi_condensed_extrabold, FontWeight.ExtraBold),
          Font(R.font.roboto_semi_condensed_black, FontWeight.Black),
      )

  // App folder name
  const val APP_DEFAULT_FOLDER_NAME = "TransferListen"
}
