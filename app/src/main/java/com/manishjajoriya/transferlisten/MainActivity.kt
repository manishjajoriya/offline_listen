package com.manishjajoriya.transferlisten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.manishjajoriya.transferlisten.presentation.homeScreen.HomeViewModel
import com.manishjajoriya.transferlisten.presentation.navgraph.NavGraph
import com.manishjajoriya.transferlisten.ui.theme.Pink
import com.manishjajoriya.transferlisten.ui.theme.TransferListenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    val homeViewModel: HomeViewModel by viewModels()
    enableEdgeToEdge()
    setContent {
      val navController = rememberNavController()
      TransferListenTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
              FloatingActionButton(
                  modifier = Modifier.padding(bottom = 40.dp, end = 32.dp).size(64.dp),
                  onClick = { homeViewModel.streamPlaylist() },
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
          NavGraph(Modifier.padding(innerPadding), navController)
        }
      }
    }
  }
}
