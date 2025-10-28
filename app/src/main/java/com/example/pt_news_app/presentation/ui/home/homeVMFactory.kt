package com.example.pt_news_app.presentation.ui.home

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pt_news_app.data.local.db.NewsDatabase
import com.example.pt_news_app.data.remote.api.ApiClient
import com.example.pt_news_app.data.repository.NewsRepositoryImpl
import com.example.pt_news_app.data.repository.UserRepositoryImpl
import com.example.pt_news_app.domain.repository.NewsRepository
import com.example.pt_news_app.domain.usecase.GetNewsUseCase
import com.example.pt_news_app.domain.usecase.LoginUserUseCase

@Composable
fun homeVmFactory(): ViewModelProvider.Factory {
    val api = ApiClient.getInstance()
    val repo = NewsRepositoryImpl(api)
    val useCase = GetNewsUseCase(repo)

    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            HomeViewModel(useCase) as T
    }
}


