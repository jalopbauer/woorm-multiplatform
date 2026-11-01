package com.indigo.woorm.books.detail.viewModel.action

class Rate(val rating: Double): BookDetailAction {
    override val description: String
        get() = "Rate book"
}