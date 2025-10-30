package com.example.pt_news_app.presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pt_news_app.data.remote.api.ApiClient
import com.example.pt_news_app.data.repository.NewsRepositoryImpl
import com.example.pt_news_app.domain.usecase.LoadNewsUseCase

@Composable
fun HomeVmFactory(): ViewModelProvider.Factory {
    val api = ApiClient.getInstance()
    val repo = NewsRepositoryImpl(api)
    val useCase = LoadNewsUseCase(repo)

    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            HomeViewModel(useCase) as T
    }
}


