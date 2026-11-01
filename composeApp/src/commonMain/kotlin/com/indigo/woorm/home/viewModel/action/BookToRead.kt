package com.indigo.woorm.home.viewModel.action

import com.indigo.woorm.library.books.LibraryBookPreview

data class BookToRead(val libraryBookPreview: LibraryBookPreview) : HomeAction {
    override val description: String = "Read ${libraryBookPreview.title}"
}
