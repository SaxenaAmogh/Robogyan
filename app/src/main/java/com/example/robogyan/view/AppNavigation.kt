package com.example.robogyan.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ){
        composable("splash"){
            SplashScreen(navController)
        }
//        composable("landing"){
//            LandingPage(navController)
//        }
        composable("login"){
            LoginPage(navController)
        }
//        composable("otp"){
//            OtpPage(navController)
//        }
//        composable("home"){
//            HomePage(navController)
//        }
//        composable("explore"){
//            ExplorePage(navController)
//        }
//        composable("cart"){
//            CartPage(navController)
//        }
//        composable("profile"){
//            ProfilePage(navController)
//        }
    }
}