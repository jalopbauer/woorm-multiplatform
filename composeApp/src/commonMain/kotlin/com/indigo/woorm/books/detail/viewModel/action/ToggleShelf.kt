package com.indigo.woorm.books.detail.viewModel.action

import com.indigo.woorm.library.books.shelves.LibraryShelf

class ToggleShelf(val libraryShelf: LibraryShelf) : BookDetailAction {
    override val description: String
        get() = "Toggle shelf"
}

