package com.example.pt_news_app.presentation.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pt_news_app.R
import com.example.pt_news_app.presentation.navigation.NavRoutes

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_color_cream)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_login),
            contentDescription = "login image",
            modifier = Modifier
                .size(400.dp, 250.dp)
        )
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text(text = "Email")
        })
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(text = "Password")
        }, visualTransformation = PasswordVisualTransformation())
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    // TODO: Replace with your actual login check
                    onLoginSuccess() // 🔹 navigate to home
                } else {
                    Toast.makeText(
                        context,
                        "Please enter email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, modifier = Modifier
                .size(100.dp, 50.dp)
        ) {
            Text(text = "Login")
        }
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End


        ) {
            Text(
                text = "Do not have an account? ",
                fontSize = 14.sp
            )
            Text(
                text = "Sign up ",
                fontSize = 16.sp,
                color = colorResource(R.color.light_blue),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavRoutes.screenSignup)
                    }
            )

        }

    }

}