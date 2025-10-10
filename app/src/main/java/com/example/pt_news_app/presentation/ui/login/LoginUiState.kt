package com.example.pt_news_app.presentation.ui.login

import com.example.pt_news_app.domain.model.User

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginUser: User? = null,
    val isLoading: Boolean = false,
    val error: String? = ""
)