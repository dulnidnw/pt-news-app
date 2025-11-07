package com.example.pt_news_app.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.data.datastore.UserPreferences
import com.example.pt_news_app.data.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepositoryImpl,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        getCurrentUser()
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            // Collect the saved email from DataStore
            userPreferences.userEmail.collect { email ->
                if (!email.isNullOrBlank()) {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                    try {
                        val user = repository.getUserByEmail(email)
                        _uiState.value = _uiState.value.copy(
                            user = user,
                            isLoading = false,
                            error = null
                        )
                    } catch (e: Exception) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        user = null,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUser()
            _uiState.value = ProfileUiState()
        }
    }
}
