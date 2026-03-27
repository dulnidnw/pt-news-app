package com.example.pt_news_app.presentation.ui.signup

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
import androidx.compose.runtime.collectAsState
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
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pt_news_app.R
import com.example.pt_news_app.presentation.navigation.NavRoutes

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignupViewModel = viewModel(factory = signUpVmFactory())
) {

    val uiState by viewModel.ui.collectAsState()
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
        OutlinedTextField(value = firstName, onValueChange = {
            firstName = it
            viewModel.onFirstName(it)
        }, label = {
            Text(text = "First Name")
        })
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = lastName, onValueChange = {
            lastName = it
            viewModel.onLastName(it)
        }, label = {
            Text(text = "Last Name")
        })
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )

        OutlinedTextField(value = email, onValueChange = {
            email = it
            viewModel.onEmail(it)
        }, label = {
            Text(text = "Email")
        })
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = password, onValueChange = {
            password = it
            viewModel.onPassword(it)
        }, label = {
            Text(text = "Password")
        }, visualTransformation = PasswordVisualTransformation())
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        OutlinedTextField(value = confirmPassword, onValueChange = {
            confirmPassword = it
            viewModel.onConfirm(it)
        }, label = {
            Text(text = "Confirm Password")
        }, visualTransformation = PasswordVisualTransformation())
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        Button(
            onClick = {
                viewModel.submit(
                    firstName.trim(),
                    lastName.trim(),
                    email.trim(),
                    password.trim(),
                    confirmPassword.trim()
                )
            },
            modifier = Modifier
                .size(width = 120.dp, height = 48.dp)
        ) {
            Text(text = if (uiState.isLoading) "Loading..." else "Sign Up")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account? ", fontSize = 14.sp)
            Text(
                text = "Login",
                fontSize = 16.sp,
                color = colorResource(R.color.light_blue),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(NavRoutes.screenLogin)
                }
            )
        }
    }


    when {
        uiState.error != null -> {
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }

        uiState.createdUser != null && !uiState.isLoading -> {
            Toast.makeText(context, "Sign up complete", Toast.LENGTH_SHORT).show()
            firstName = ""
            lastName = ""
            email = ""
            password = ""
            confirmPassword = ""
            navController.navigate(NavRoutes.screenLogin) {
                popUpTo(NavRoutes.screenLogin) { inclusive = true }
            }
        }
    }

}