package com.manishjajoriya.transferlisten.presentation.searchScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen(modifier: Modifier = Modifier, title : String?) {
  Text(title.toString(), modifier)
}