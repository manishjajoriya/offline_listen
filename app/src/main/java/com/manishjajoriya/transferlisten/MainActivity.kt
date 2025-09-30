package com.manishjajoriya.transferlisten

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.transferlisten.presentation.homeScreen.HomeScreen
import com.manishjajoriya.transferlisten.presentation.homeScreen.HomeViewModel
import com.manishjajoriya.transferlisten.ui.theme.Pink
import com.manishjajoriya.transferlisten.ui.theme.TransferListenTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    val homeViewModel: HomeViewModel by viewModels()
    enableEdgeToEdge()
    setContent {
      TransferListenTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
              FloatingActionButton(
                  modifier = Modifier.padding(bottom = 40.dp, end = 32.dp).size(64.dp),
                  onClick = {
                    askNotificationPermission(this)
                    homeViewModel.viewModelScope.launch {
                      homeViewModel.streamPlaylist()
                      homeViewModel.downloadPlaylist()
                    }
                  },
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

fun askNotificationPermission(activity: Activity) {
  if (
      ContextCompat.checkSelfPermission(activity, android.Manifest.permission.POST_NOTIFICATIONS) !=
          PackageManager.PERMISSION_GRANTED
  ) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      ActivityCompat.requestPermissions(
          activity,
          arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
          1,
      )
    }
  }
}
