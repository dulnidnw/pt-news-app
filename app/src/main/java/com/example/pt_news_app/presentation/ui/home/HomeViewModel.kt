package com.example.pt_news_app.presentation.ui.home

import android.R.attr.apiKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.repository.NewsRepository
import com.example.pt_news_app.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val repo: GetNewsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState
    private val apiKey = "a6a1ce5e0a4b4baf9b4fb649b90d241d"
    fun loadNews(query: String? = null) {
        val currentState = _uiState.value

        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)

//            val result = repo.invoke(
//                query = currentState.query,
//                from = currentState.from,
//                sortBy = currentState.sortBy
//            )
            val result = repo.invoke("us", "business")

            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(
                    articles = result.getOrDefault(emptyList()),
                    isLoading = false,
                    error = null
                )
            } else {
                _uiState.value.copy(
                    error = result.exceptionOrNull()?.message,
                    isLoading = false
                )
            }
        }

    }

}
