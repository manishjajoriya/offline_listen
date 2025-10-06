package com.manishjajoriya.transferlisten.presentation.navgraph

sealed class Routes(val route : String) {
  object HomeScreen : Routes("homeScreen")
  object SearchScreen : Routes("searchScreen")
}