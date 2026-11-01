package com.indigo.woorm.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.users.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    userPrefs: UserPreferencesRepository
) : ViewModel() {

    val appState: StateFlow<AppState> = combine(
        userPrefs.userIdFlow,
        userPrefs.usernameFlow
    ) { userId, username ->
        if (userId.isNullOrBlank() || username.isNullOrBlank()) {
            AppState.Unauthenticated
        } else {
            AppState.Authenticated
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppState.Loading
    )
}