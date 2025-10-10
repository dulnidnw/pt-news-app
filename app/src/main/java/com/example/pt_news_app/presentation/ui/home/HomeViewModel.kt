package com.example.pt_news_app.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun loadNews() {
        val currentState = _uiState.value

        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)

            val result = repo.invoke(
                query = currentState.query,
                from = currentState.from,
                sortBy = currentState.sortBy
            )

            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(
                    articles = result.getOrDefault(emptyList()),
                    isLoading = false
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
