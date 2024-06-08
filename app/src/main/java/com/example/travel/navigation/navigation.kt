package com.example.travel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travel.ui.screen.AccountScreen
import com.example.travel.ui.screen.HomeScreen
import com.example.travel.ui.screen.InfoPlaceScreen
import com.example.travel.ui.screen.ReviewScreen
import com.example.travel.ui.screen.SearchScreen
import com.example.travel.ui.screen.ToDoScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home"){
            HomeScreen(navController = navController)
        }
        composable("search"){
            SearchScreen(navController = navController)
        }
        composable("to-do"){
            ToDoScreen(navController = navController)
        }
        composable("review"){
            ReviewScreen(navController = navController)
        }
        composable("account"){
            AccountScreen(navController = navController)
        }
        composable("details/{id}/{lat}/{lng}", arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("lat") { type = NavType.StringType },
            navArgument("lng") { type = NavType.StringType }
        )) {
            InfoPlaceScreen(navController = navController)
        }
    }
}