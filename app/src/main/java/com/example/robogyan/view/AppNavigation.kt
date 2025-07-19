package com.example.robogyan.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.robogyan.view.secpages.AddAssetPage
import com.example.robogyan.view.secpages.AssetViewPage
import com.example.robogyan.view.secpages.ProjectViewPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home",
    ){
        //start pages
        composable("splash"){
            SplashScreen(navController)
        }
        composable("login"){
            LoginPage(navController)
        }
        composable("start"){
            StartPage(navController)
        }

        //main pages
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

        //secondary pages
        composable("assetview"){
            AssetViewPage(navController)
        }
        composable("addasset"){
            AddAssetPage(navController)
        }
        composable("projectview"){
            ProjectViewPage(navController)
        }
    }
}