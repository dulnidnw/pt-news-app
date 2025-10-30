package com.example.pt_news_app.presentation.ui.seeAll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.domain.repository.NewsRepository
import com.example.pt_news_app.domain.usecase.SearchNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchNewsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState

    fun loadNews() = viewModelScope.launch {
        val result = repository.getFilterNewsFeed()
        result.onSuccess { articles ->
            uiState.value = uiState.value.copy(
                articles = articles,
                filteredArticles = articles
            )
        }
    }

    fun filterNews(query: String) {
        val filtered = if (query.isBlank()) {
            uiState.value.articles
        } else {
            uiState.value.articles.filter {
                it.title?.contains(query, ignoreCase = true) == true
            }
        }

        uiState.value = uiState.value.copy(filteredArticles = filtered)
    }
//    fun searchNews(query: String) {
//        val filtered = searchNewsUseCase(_uiState.value.articles, query)
//        _uiState.value = _uiState.value.copy(filteredArticles = filtered)
//    }
}
