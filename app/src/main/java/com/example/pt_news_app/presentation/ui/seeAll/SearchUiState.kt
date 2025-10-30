package com.example.pt_news_app.presentation.ui.seeAll

import com.example.pt_news_app.data.remote.dto.Article

data class SearchUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val filteredArticles: List<Article> = emptyList(),
    val error: String? = null
)

