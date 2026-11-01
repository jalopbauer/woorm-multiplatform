package com.indigo.woorm.home.viewModel.action

import com.indigo.woorm.library.books.LibraryBookPreview

data class BookToDnf(val libraryBookPreview: LibraryBookPreview) : HomeAction {
    override val description: String = "Dnf ${libraryBookPreview.title}"
}
