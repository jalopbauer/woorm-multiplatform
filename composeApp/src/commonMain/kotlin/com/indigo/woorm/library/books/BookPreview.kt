package com.indigo.woorm.library.books

import com.indigo.woorm.common.book.Author
import com.indigo.woorm.common.book.Book

data class LibraryBookPreview(
    override val id: Int,
    override val coverUrl: String,
    override val title: String,
    override val authors: List<Author>,
) : Book