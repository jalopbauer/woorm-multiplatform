package com.indigo.woorm.viewModel.action

internal interface ActionableViewModel<T : Action> {
    fun act(action: T)
}