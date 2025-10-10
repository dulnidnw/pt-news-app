package com.example.pt_news_app.presentation.ui.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.pt_news_app.data.repository.UserRepositoryImpl
import com.example.pt_news_app.data.local.db.NewsDatabase
import com.example.pt_news_app.domain.usecase.SignUpUseCase

@Composable
fun signUpVmFactory(): ViewModelProvider.Factory {
    val app = (LocalContext.current.applicationContext as Application)
    val db = NewsDatabase.get(app)
    val repo = UserRepositoryImpl(db.userDao())
    val useCase = SignUpUseCase(repo)

    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SignupViewModel(useCase) as T
    }
}