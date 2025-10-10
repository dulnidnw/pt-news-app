package com.example.pt_news_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pt_news_app.presentation.ui.home.HomeScreen
import com.example.pt_news_app.presentation.ui.home.HomeViewModel
import com.example.pt_news_app.presentation.ui.login.LoginScreen
import com.example.pt_news_app.presentation.ui.signup.SignUpScreen
import com.example.pt_news_app.presentation.ui.signup.SignupViewModel
import com.example.pt_news_app.presentation.ui.login.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.screenLogin, builder = {
        composable(NavRoutes.screenLogin) {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(
                navController, viewModel,
                onLoginSuccess = TODO()
            )
        }

        composable(NavRoutes.screenLogin) {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(navController, viewModel = viewModel, onLoginSuccess = {
                navController.navigate(NavRoutes.screenHome) {
                    popUpTo(NavRoutes.screenLogin) {
                        inclusive = true
                    } // removes Login from backstack
                }
            }
            )
        }
        composable(NavRoutes.screenSignup) {
            val viewModel: SignupViewModel = viewModel()
            SignUpScreen(navController, viewModel)
        }
        composable(NavRoutes.screenHome) {
            val apiKey = "YOUR_NEWS_API_KEY"
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(navController, viewModel = viewModel)
        }
    })
}
