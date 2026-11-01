package com.indigo.woorm.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.users.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userService: UserService
) : ViewModel() {

    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun register(username: String) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading
            userService.register(username)
                .onSuccess { _state.value = AuthUiState.Idle }
                .onFailure { _state.value = AuthUiState.Error(it.message ?: "Something went wrong") }
        }
    }

    fun login(id: String) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading
            userService.login(id)
                .onSuccess { _state.value = AuthUiState.Idle }
                .onFailure { _state.value = AuthUiState.Error(it.message ?: "Something went wrong") }
        }
    }

    fun clearError() {
        _state.value = AuthUiState.Idle
    }
}

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    data class Error(val message: String) : AuthUiState
}
