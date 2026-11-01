package com.indigo.woorm.books.detail.viewModel.action

class CreateShelf(val newShelfName: String) : BookDetailAction {
    override val description: String
        get() = "Create shelf"
}