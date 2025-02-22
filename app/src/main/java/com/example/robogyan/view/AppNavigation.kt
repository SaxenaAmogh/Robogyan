package com.example.robogyan.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("splash"){
            SplashScreen(navController)
        }
        composable("member"){
            MemberPage(navController)
        }
        composable("login"){
            LoginPage(navController)
        }
        composable("logs"){
            LogPage(navController)
        }
        composable("home"){
            HomePage(navController)
        }
        composable("profile"){
            ProfilePage(navController)
        }
    }
}