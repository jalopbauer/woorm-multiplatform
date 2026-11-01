package com.indigo.woorm.app.viewmodel

sealed class AppState {
    object Loading : AppState()
    object Authenticated : AppState()
    object Unauthenticated : AppState()
}