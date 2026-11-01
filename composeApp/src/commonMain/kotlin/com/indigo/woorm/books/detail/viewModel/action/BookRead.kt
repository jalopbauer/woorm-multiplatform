package com.indigo.woorm.books.detail.viewModel.action

class BookRead(
    val finishedToday: Boolean
): BookDetailAction {
    override val description: String = "Book read"
}
