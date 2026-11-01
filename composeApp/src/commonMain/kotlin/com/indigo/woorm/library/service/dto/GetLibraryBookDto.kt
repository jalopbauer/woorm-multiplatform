package com.indigo.woorm.library.service.dto

import com.indigo.woorm.books.service.dto.ShelfDto
import com.indigo.woorm.books.service.dto.toShelf
import com.indigo.woorm.library.books.LibraryBook
import kotlinx.serialization.Serializable

@Serializable
data class GetLibraryBookDto(
    val libraryBookStatus: LibraryBookStatusDTO? = null,
    val libraryBookStatuses: List<LibraryBookStatusDTO>,
    val shelves: List<ShelfDto>,
    val finalComment: String? = null,
)

fun GetLibraryBookDto.toLibraryBook(): LibraryBook =
    LibraryBook(
        currentStatus = this.libraryBookStatus?.toLibraryBookStatus(),
        shelves = this.shelves.toShelf(),
        statuses = this.libraryBookStatuses.map { it.toLibraryBookStatus() },
        finalComment = this.finalComment,
    )