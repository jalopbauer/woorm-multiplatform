package com.indigo.woorm.library.books

import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.library.books.status.LibraryBookStatus

data class LibraryBook(
    val currentStatus: LibraryBookStatus?,
    val statuses: List<LibraryBookStatus>,
    val shelves: List<LibraryBookShelf>,
    val finalComment: String?,
)
