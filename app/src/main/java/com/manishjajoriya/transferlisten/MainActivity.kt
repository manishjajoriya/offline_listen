package com.manishjajoriya.transferlisten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.manishjajoriya.transferlisten.presentation.homeScreen.HomeScreen
import com.manishjajoriya.transferlisten.ui.theme.Pink
import com.manishjajoriya.transferlisten.ui.theme.TransferListenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      TransferListenTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
              FloatingActionButton(
                  modifier = Modifier.padding(bottom = 40.dp, end = 32.dp).size(64.dp),
                  onClick = {},
                  containerColor = Pink,
                  shape = CircleShape,
                  content = {
                    Icon(
                        painter = painterResource(R.drawable.outlined_cloud_download),
                        contentDescription = "Download",
                        modifier = Modifier.size(36.dp),
                    )
                  },
              )
            },
        ) { innerPadding ->
          HomeScreen(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}
