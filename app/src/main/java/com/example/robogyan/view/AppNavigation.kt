package com.example.robogyan.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home",
    ){
        composable("splash"){
            SplashScreen(navController)
        }
        composable("login"){
            LoginPage(navController)
        }
        composable("home"){
            HomePage(navController)
        }
        composable("member"){
            MemberPage(navController)
        }
        composable("security"){
            SecurityPage(navController)
        }
        composable("projects"){
            ProjectPage(navController)
        }
        composable("resources"){
            ResourcesPage(navController)
        }
        composable("profile"){
            ProfilePage(navController)
        }
    }
}