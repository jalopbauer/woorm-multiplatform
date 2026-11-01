package com.indigo.woorm.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.users.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            userPrefs.clearUserData()
        }
    }
}