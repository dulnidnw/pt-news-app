package com.example.pt_news_app.presentation.ui.seeAll

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pt_news_app.data.remote.api.ApiClient
import com.example.pt_news_app.data.repository.NewsRepositoryImpl
import com.example.pt_news_app.domain.usecase.LoadNewsUseCase
import com.example.pt_news_app.domain.usecase.SearchNewsUseCase
import com.example.pt_news_app.presentation.ui.home.HomeViewModel


@Composable
fun SearchVmFactory(): ViewModelProvider.Factory {
    val api = ApiClient.getInstance()
    val repo = NewsRepositoryImpl(api)
    val useCase = SearchNewsUseCase(repo)

    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SearchViewModel(useCase) as T
    }
}