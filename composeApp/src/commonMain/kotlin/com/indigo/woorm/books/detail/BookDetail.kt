package com.indigo.woorm.books.detail

import com.indigo.woorm.common.book.Author
import com.indigo.woorm.common.book.Book
import com.indigo.woorm.common.book.Language
import com.indigo.woorm.common.book.Tag
import kotlinx.serialization.Serializable

@Serializable
data class BookDetail(
    override val id: Int,
    override val coverUrl: String,
    override val title: String,
    override val authors: List<Author>,
    val tags: List<Tag>,
    val isbn: String?,
    val pages: Int,
    val language: Language,
    val abstract: String,
) : Book
