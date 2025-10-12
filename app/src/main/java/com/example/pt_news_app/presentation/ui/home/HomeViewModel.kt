package com.example.pt_news_app.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val repo: GetNewsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState
    private val apiKey = "a6a1ce5e0a4b4baf9b4fb649b90d241d"
    fun loadNews(query: String? = null) {
        val currentState = _uiState.value
        _uiState.value = HomeUiState(isLoading = true)

        viewModelScope.launch {
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

    suspend fun loadNeesFeed(category: String) {
        val resultNewsFeed = repo.getNewsByCategory(category)
        _uiState.value = if (resultNewsFeed.isSuccess) {
            _uiState.value.copy(
                articles = resultNewsFeed.getOrDefault(emptyList()),
                isLoading = false,
                error = null
            )
        } else {
            _uiState.value.copy(
                error = resultNewsFeed.exceptionOrNull()?.message,
                isLoading = false
            )
        }
    }
}
