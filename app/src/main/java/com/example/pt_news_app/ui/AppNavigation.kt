package com.example.pt_news_app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pt_news_app.ui.auth.LoginScreen
import com.example.pt_news_app.ui.auth.SignUpScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.screenLogin, builder = {
        composable(NavRoutes.screenLogin){
            LoginScreen(navController)
        }
        composable(NavRoutes.screenSignup){
            SignUpScreen(navController)
        }
    })
}
