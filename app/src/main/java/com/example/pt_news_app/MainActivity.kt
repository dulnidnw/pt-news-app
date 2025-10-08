package com.example.pt_news_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pt_news_app.ui.AppNavigation

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