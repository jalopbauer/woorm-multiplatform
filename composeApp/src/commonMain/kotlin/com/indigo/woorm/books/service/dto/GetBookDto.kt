package com.indigo.woorm.books.service.dto

import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.common.book.Author
import com.indigo.woorm.common.book.Language
import com.indigo.woorm.common.book.Tag
import kotlinx.serialization.Serializable

@Serializable
data class GetBookDto(
    val id: Int,
    val title: String,
    val pages: Int,
    val description: String,
    val coverUrl: String,
    val isbn: String?,
    val language: LanguageDto,
    val authors: List<AuthorDto>,
    val tags: List<TagDto>
)

fun GetBookDto.toBookDetail(): BookDetail =
    BookDetail(
        id = this.id,
        coverUrl = this.coverUrl,
        title = this.title,
        authors = this.authors.map { Author(it.id, it.name) },
        tags = this.tags.map { Tag(it.id, it.name) },
        isbn = this.isbn,
        pages = this.pages,
        language = Language(this.language.id, this.language.name),
        abstract = this.description,
    )