package com.example.pt_news_app.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {
    private val _ui = MutableStateFlow(LoginUiState())
    val ui: StateFlow<LoginUiState> = _ui.asStateFlow()

    fun onEmail(v: String) = _ui.update { it.copy(email = v, error = null) }
    fun onPassword(v: String) = _ui.update { it.copy(password = v, error = null) }

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun loginUser(email: String, password: String) {
        val s = _ui.value
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null) }
            val result = loginUserUseCase( s.email, s.password)
            _ui.update {
                it.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message,
                    loginUser = result.getOrNull()
                )
            }
        }
    }

//    fun loginUser(email: String, password: String) {
//        viewModelScope.launch {
//            _loginSuccess.value = loginUserUseCase(email, password)
//        }
//    }
}