package com.example.robogyan.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.robogyan.view.secpages.AddAssetPage
import com.example.robogyan.view.secpages.AddProjectPage
import com.example.robogyan.view.secpages.AddUsagePage
import com.example.robogyan.view.secpages.AssetViewPage
import com.example.robogyan.view.secpages.ChangePasswordPage
import com.example.robogyan.view.secpages.ProjectViewPage
import com.example.robogyan.view.secpages.UpdateMemberPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
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
        composable("addasset"){
            AddAssetPage(navController)
        }
        composable("changePassword"){
            ChangePasswordPage(navController)
        }
        composable(
            route = "assetview/{assetId}",
            arguments = listOf(navArgument("assetId") { type = NavType.IntType })
        ) { backStackEntry ->
            val assetId = backStackEntry.arguments?.getInt("assetId") ?: 0
            AssetViewPage(navController, assetId)
        }
        composable(
            route = "projectview/{projectId}",
            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getInt("projectId") ?: 0
            ProjectViewPage(navController, projectId)
        }
        composable(
            route = "editProjects/{projectId}",
            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getInt("projectId") ?: 0
            AddProjectPage(navController, projectId)
        }
        composable(
            route = "updateMember/{memberId}",
            arguments = listOf(navArgument("memberId") { type = NavType.StringType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
            UpdateMemberPage(navController, memberId)
        }
        composable(
            route = "editAssetUsage/{assetId}/{usageId}",
            arguments = listOf(
                navArgument("assetId") { type = NavType.IntType },
                navArgument("usageId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val assetId = backStackEntry.arguments?.getInt("assetId") ?: 0
            val usageId = backStackEntry.arguments?.getInt("usageId") ?: 0

            AddUsagePage(navController, assetId, usageId)
        }
    }
}