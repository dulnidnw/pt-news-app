package com.example.pt_news_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pt_news_app.ui.AppNavigation
import com.example.pt_news_app.ui.NavRoutes
import com.example.pt_news_app.ui.auth.LoginScreen
import com.example.pt_news_app.ui.auth.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

//            val navController = rememberNavController()
            AppNavigation()
//            NavHost(navController = navController, startDestination = NavRoutes.screenLogin, builder = {
//               composable(NavRoutes.screenLogin){
//                   LoginScreen(navController)
//               }
//                composable(NavRoutes.screenSignup) {
//                    SignUpScreen()
//                }
//            })


        }
    }
}