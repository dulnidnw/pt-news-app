package com.example.pt_news_app.presentation.ui.home

import com.example.pt_news_app.data.remote.dto.Article

data class HomeUiState(
    val query: String = "",
    val articles: List<Article> = emptyList(),
    val from: String = "",
    val sortBy: String = "",
    val isLoading: Boolean = false,
    val error: String? = ""
)