package com.example.pt_news_app.presentation.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.usecase.SignUpUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
//@HiltAndroidApp
class SignupViewModel(private val signUp: SignUpUseCase) : ViewModel() {
    private val _ui = MutableStateFlow(SignUpUiState())
    val ui: StateFlow<SignUpUiState> = _ui.asStateFlow()

    fun onFirstName(v: String) = _ui.update { it.copy(firstName = v, error = null) }
    fun onLastName(v: String) = _ui.update { it.copy(lastName = v, error = null) }
    fun onEmail(v: String) = _ui.update { it.copy(email = v, error = null) }
    fun onPassword(v: String) = _ui.update { it.copy(password = v, error = null) }
    fun onConfirm(v: String) = _ui.update { it.copy(confirmPassword = v, error = null) }

    fun submit() {
        val s = _ui.value
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null) }
            val result = signUp(s.firstName, s.lastName, s.email, s.password, s.confirmPassword)
            _ui.update {
                it.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message,
                    createdUser = result.getOrNull()
                )
            }
        }
    }
}