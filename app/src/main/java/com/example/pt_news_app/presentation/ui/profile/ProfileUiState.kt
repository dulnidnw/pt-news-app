package com.example.pt_news_app.presentation.ui.profile

import com.example.pt_news_app.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
