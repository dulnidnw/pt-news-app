package com.example.pt_news_app.presentation.navigation

import FavouritesScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.presentation.ui.home.HomeScreen
import com.example.pt_news_app.presentation.ui.home.HomeViewModel
import com.example.pt_news_app.presentation.ui.home.HomeVmFactory
import com.example.pt_news_app.presentation.ui.login.LoginScreen
import com.example.pt_news_app.presentation.ui.login.LoginViewModel
import com.example.pt_news_app.presentation.ui.login.loginVmFactory
import com.example.pt_news_app.presentation.ui.newsDetail.DetailScreen
import com.example.pt_news_app.presentation.ui.profile.ProfileScreen
import com.example.pt_news_app.presentation.ui.seeAll.SearchViewModel
import com.example.pt_news_app.presentation.ui.seeAll.SearchVmFactory
import com.example.pt_news_app.presentation.ui.seeAll.SeeAllScreen
import com.example.pt_news_app.presentation.ui.signup.SignUpScreen
import com.example.pt_news_app.presentation.ui.signup.SignupViewModel
import com.example.pt_news_app.presentation.ui.signup.signUpVmFactory
import com.google.gson.Gson
import kotlin.String
import kotlin.jvm.java

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.screenLogin, builder = {
        composable(NavRoutes.screenLogin) {
            val context = LocalContext.current
            val viewModel: LoginViewModel = viewModel(factory = loginVmFactory(context))
            LoginScreen(navController, viewModel = viewModel, onLoginSuccess = {
                navController.navigate(NavRoutes.screenHome) {
                    popUpTo(NavRoutes.screenLogin) { inclusive = true }
                }
            })
        }
        composable(NavRoutes.screenSignup) {
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

        composable(
            route = "${NavRoutes.screenNewsDetails}/{article}",
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("article")
            val article = Gson().fromJson(articleJson, Article::class.java)
            DetailScreen(
                article.title,
                article.author ?: "",
                article.description ?: "",
                article.urlToImage ?: "",
                article.author ?: "",
                article.formattedDateTime ?: "",
                article.content ?: ""
            )
        }

//        composable(
//            route = "detailScreen/{articles}",
//            arguments = listOf(navArgument("articles") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val json = backStackEntry.arguments?.getString("articles")
//            val articles: List<Article> = Gson().fromJson(json, Array<Article>::class.java).toList()
//            DetailScreen(articles)
//        }

    })
}
