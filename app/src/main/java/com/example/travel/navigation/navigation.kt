package com.example.travel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travel.data.`object`.AuthViewModel
import com.example.travel.ui.screen.AccountScreen
import com.example.travel.ui.screen.HomeScreen
import com.example.travel.ui.screen.InfoPlaceScreen
import com.example.travel.ui.screen.LoginScreen
import com.example.travel.ui.screen.ReviewScreen
import com.example.travel.ui.screen.SearchScreen
import com.example.travel.ui.screen.SignupScreen
import com.example.travel.ui.screen.ToDoScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val user = AuthViewModel().user?.email
    NavHost(
        navController = navController,
        startDestination = if(user == null){
            "login"
        } else {
            "home"
        }
    ) {
        composable("home"){
            HomeScreen(navController = navController)
        }
        composable("signup"){
            SignupScreen(navController = navController)
        }
        composable("login"){
            LoginScreen(navController = navController)
        }
        composable("search")
        {
            SearchScreen(navController = navController)
        }
        composable("search/{query}", arguments = listOf(
            navArgument("query"){type = NavType.StringType}))
        {
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