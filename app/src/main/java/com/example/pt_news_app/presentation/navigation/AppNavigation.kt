package com.example.pt_news_app.presentation.navigation

import FavouritesScreen
import android.R.attr.subtitle
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.presentation.ui.seeAll.SeeAllScreen
import com.example.pt_news_app.presentation.ui.home.HomeScreen
import com.example.pt_news_app.presentation.ui.home.HomeViewModel
import com.example.pt_news_app.presentation.ui.home.HomeVmFactory
import com.example.pt_news_app.presentation.ui.login.LoginScreen
import com.example.pt_news_app.presentation.ui.signup.SignUpScreen
import com.example.pt_news_app.presentation.ui.signup.signUpVmFactory
import com.example.pt_news_app.presentation.ui.login.LoginViewModel
import com.example.pt_news_app.presentation.ui.login.loginVmFactory
import com.example.pt_news_app.presentation.ui.newsDetail.DetailScreen
import com.example.pt_news_app.presentation.ui.profile.ProfileScreen
import com.example.pt_news_app.presentation.ui.seeAll.SearchViewModel
import com.example.pt_news_app.presentation.ui.seeAll.SearchVmFactory
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
            val viewModel: HomeViewModel = viewModel(factory = HomeVmFactory())
            HomeScreen(navController, viewModel = viewModel)
        }
        composable(NavRoutes.screenProfile) {
            ProfileScreen(navController)
        }
        composable(NavRoutes.screenFavorite) {
            FavouritesScreen(navController)
        }
        composable(NavRoutes.screenSeeAll) {

            val viewModel: SearchViewModel = viewModel(factory = SearchVmFactory())
            SeeAllScreen(navController, viewModel = viewModel)
        }

        composable(NavRoutes.screenNewsDetails) { backStackEntry ->
            val savedStateHandle = backStackEntry.savedStateHandle
            val article = savedStateHandle.get<Article>("article")
            article?.let {
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val subtitle = backStackEntry.arguments?.getString("subtitle") ?: ""
                val description = backStackEntry.arguments?.getString("description") ?: ""
                val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
                DetailScreen(title, subtitle, description, imageUrl)

            }
        }
        /*composable(
            "details/{title}/{subtitle}/{title}/{description}/{imageUrl}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("subtitle") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val subtitle = backStackEntry.arguments?.getString("subtitle") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            DetailScreen(title, subtitle,description, imageUrl)
        }*/

    })
}
