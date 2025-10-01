package com.manishjajoriya.transferlisten.presentation.homeScreen.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.manishjajoriya.transferlisten.ui.theme.Pink

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProgressIndicator() {
  CircularWavyProgressIndicator(
      modifier = Modifier.size(50.dp),
      color = Pink,
      trackColor = Color.Gray.copy(alpha = 0.3f),
      amplitude = 1.0f,
      wavelength = 20.dp,
      waveSpeed = 16.dp,
  )
}
