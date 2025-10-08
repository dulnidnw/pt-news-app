package com.example.pt_news_app.ui.auth

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pt_news_app.R
import com.example.pt_news_app.ui.NavRoutes

@Composable
fun SignUpScreen(navController: NavController) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_color_cream)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = "firstName", onValueChange = {
            firstName = it
        }, label = {
            Text(text = "First Name")
        })
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = "lastName", onValueChange = {
            lastName = it
        }, label = {
            Text(text = "Last Name")
        })
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )

        OutlinedTextField(value = "email", onValueChange = {
            email = it
        }, label = {
            Text(text = "Email")
        })
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = "password", onValueChange = {
            password = it
        }, label = {
            Text(text = "Password")
        }, visualTransformation = PasswordVisualTransformation())
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = "confirmPassword", onValueChange = {
            confirmPassword = it
        }, label = {
            Text(text = "Confirm Password")
        }, visualTransformation = PasswordVisualTransformation())
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        Button(
            onClick = {
                Log.i("Credentials", "Email: $email Password $password")
            }, modifier = Modifier
                .size(100.dp, 50.dp)
        ) {
            Text(text = "Sign up")
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
                text = "Already have an account? ",
                fontSize = 14.sp
            )
            Text(
                text = "Login ",
                fontSize = 16.sp,
                color = colorResource(R.color.light_blue),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavRoutes.screenLogin)
//                        val intent = Intent(context, SignUpActivity::class.java)
//                        context.startActivity(intent)
                    }
            )
        }

    }

}