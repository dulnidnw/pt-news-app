package com.example.pt_news_app.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.data.datastore.UserPreferences
import com.example.pt_news_app.data.repository.UserRepositoryImpl
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: UserRepositoryImpl,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _ui = MutableStateFlow(LoginUiState())
//    val ui: StateFlow<LoginUiState> = _ui.asStateFlow()

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState


    fun onEmailChange(newEmail: String) {
        _loginState.value = _loginState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    fun loginUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value =
                _loginState.value.copy(error = "Please enter both email and password")
            return
        }

        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true, error = null)

            val result = repository.login(email.trim(), password.trim()) // trim spaces
            result.onSuccess { user ->
                userPreferences.saveUserEmail(user.email) // suspend, already in coroutine
                _loginState.value = _loginState.value.copy(
                    loginUser = user,
                    isLoading = false,
                    error = null
                )
            }.onFailure { throwable ->
                _loginState.value = _loginState.value.copy(
                    isLoading = false,
                    error = throwable.message ?: "Invalid email or password"
                )
            }
        }
    }
}