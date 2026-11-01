package com.indigo.woorm.books

import com.indigo.woorm.common.book.Author
import com.indigo.woorm.common.book.Book
import kotlinx.serialization.Serializable

@Serializable
data class BookPreview(
    override val id: Int,
    override val coverUrl: String,
    override val title: String,
    override val authors: List<Author> = emptyList(),
) : Book