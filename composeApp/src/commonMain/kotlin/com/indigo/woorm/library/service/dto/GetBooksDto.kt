package com.indigo.woorm.library.service.dto

import com.indigo.woorm.books.service.dto.AuthorDto
import com.indigo.woorm.common.book.Author
import com.indigo.woorm.library.books.LibraryBookPreview
import kotlinx.serialization.Serializable

@Serializable
data class GetLibraryBooksDto(
    val id: Int,
    val title: String,
    val coverUrl: String,
    val authors: List<AuthorDto>
)

fun List<GetLibraryBooksDto>.toLibraryBookPreview() : List<LibraryBookPreview> =
    this.map(GetLibraryBooksDto::toLibraryBookPreview)

fun GetLibraryBooksDto.toLibraryBookPreview(): LibraryBookPreview =
    LibraryBookPreview(
        id = this.id,
        title = this.title,
        coverUrl = this.coverUrl,
        authors = this.authors.map { Author(it.id, it.name) },
    )