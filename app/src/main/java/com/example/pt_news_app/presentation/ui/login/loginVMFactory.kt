package com.example.pt_news_app.presentation.ui.login

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pt_news_app.data.local.db.NewsDatabase
import com.example.pt_news_app.data.repository.UserRepositoryImpl
import com.example.pt_news_app.domain.usecase.LoginUserUseCase

@Composable
fun loginVmFactory(): ViewModelProvider.Factory {
    val app = (LocalContext.current.applicationContext as Application)
    val db = NewsDatabase.get(app)
    val repo = UserRepositoryImpl(db.userDao())
    val useCase = LoginUserUseCase(repo)

    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            LoginViewModel(useCase) as T
    }
}


