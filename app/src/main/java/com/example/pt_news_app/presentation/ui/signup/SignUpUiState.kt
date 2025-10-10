package com.example.pt_news_app.presentation.ui.signup

import com.example.pt_news_app.domain.model.User

data class SignUpUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val createdUser: User? = null,

    val isLoading: Boolean = false,
    val error: String? = ""
)
