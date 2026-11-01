package com.indigo.woorm.books.detail.viewModel.action

class SaveComment(val comment: String): BookDetailAction {
    override val description: String
        get() = "Comment saved"
}
