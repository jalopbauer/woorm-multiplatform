package com.indigo.woorm.books.detail.viewModel.action

import com.indigo.woorm.library.books.status.LibraryBookStatus

class DeleteStatusHistory(val status: LibraryBookStatus) : BookDetailAction {
    override val description: String
        get() = "Status removed from history"
}
