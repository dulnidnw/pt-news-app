package com.example.pt_news_app.presentation.navigation

import FavouritesScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pt_news_app.presentation.ui.home.HomeScreen
import com.example.pt_news_app.presentation.ui.home.HomeViewModel
import com.example.pt_news_app.presentation.ui.home.homeVmFactory
import com.example.pt_news_app.presentation.ui.login.LoginScreen
import com.example.pt_news_app.presentation.ui.signup.SignUpScreen
import com.example.pt_news_app.presentation.ui.signup.signUpVmFactory
import com.example.pt_news_app.presentation.ui.login.LoginViewModel
import com.example.pt_news_app.presentation.ui.login.loginVmFactory
import com.example.pt_news_app.presentation.ui.profile.ProfileScreen
import com.example.pt_news_app.presentation.ui.signup.SignupViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.screenLogin, builder = {
        composable(NavRoutes.screenLogin) {
            val viewModel: LoginViewModel = viewModel(factory = loginVmFactory())
            LoginScreen(navController, viewModel = viewModel, onLoginSuccess = {
                navController.navigate(NavRoutes.screenHome) {
                    popUpTo(NavRoutes.screenLogin) { inclusive = true }
                }
            })
        }
        composable(NavRoutes.screenSignup) {
            // Use the custom factory that wires dependencies
            val vm = viewModel<SignupViewModel>(
                factory = signUpVmFactory()
            )
            SignUpScreen(navController, vm)
        }
        composable(NavRoutes.screenHome) {
            val viewModel: HomeViewModel = viewModel(factory = homeVmFactory())
            HomeScreen(navController, viewModel = viewModel)
        }
        composable(NavRoutes.screenProfile) {
            ProfileScreen(navController)
        }
        composable(NavRoutes.screenFavorite) {
            FavouritesScreen(navController)
        }


    })
}
