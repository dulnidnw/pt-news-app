package com.example.pt_news_app.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(private val repo: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getTopNewsHeadlines()
    }

    fun getTopNewsHeadlines(){
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)
            val result = repo.getTopNewsHeadlines()
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(articles = result.getOrDefault(emptyList()), isLoading = false)
            } else {
                _uiState.value.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }


//    fun fetchNews(query: String, fromDate: String) {
//        viewModelScope.launch {
//            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
//            val result = repo.searchNews(query, fromDate)
//            _uiState.value = if (result.isSuccess) {
//                _uiState.value.copy(articles = result.getOrDefault(emptyList()), isLoading = false)
//            } else {
//                _uiState.value.copy(error = result.exceptionOrNull()?.message, isLoading = false)
//            }
//        }
//    }
}
