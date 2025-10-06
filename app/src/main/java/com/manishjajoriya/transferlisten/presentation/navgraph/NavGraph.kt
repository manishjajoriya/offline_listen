package com.manishjajoriya.transferlisten.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishjajoriya.transferlisten.presentation.homeScreen.HomeScreen
import com.manishjajoriya.transferlisten.presentation.homeScreen.HomeViewModel
import com.manishjajoriya.transferlisten.presentation.searchScreen.SearchScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController ) {
  val startDestination = Routes.HomeScreen.route
  val homeViewModel : HomeViewModel = hiltViewModel()
  NavHost(navController, startDestination){
    composable(Routes.HomeScreen.route) {
      HomeScreen(modifier, navController, homeViewModel)
    }
    composable(Routes.SearchScreen.route+"/{title}") {
      val title = it.arguments?.getString("title")
      SearchScreen(modifier, title)
    }
  }
}