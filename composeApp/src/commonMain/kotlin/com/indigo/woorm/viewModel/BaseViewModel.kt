package com.indigo.woorm.viewModel

import androidx.lifecycle.ViewModel
import com.indigo.woorm.viewModel.action.Action
import com.indigo.woorm.viewModel.action.ActionableViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T : Action> : ViewModel(), ActionableViewModel<T> {

    private val _action = MutableStateFlow<T?>(null)
    val success: StateFlow<T?> = _action.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun success(action: T) {
        _action.value = action
    }

    fun error(message: String) {
        _error.value = message
    }
}