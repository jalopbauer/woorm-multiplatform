package com.indigo.woorm.books.service.dto

import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import kotlinx.serialization.Serializable

@Serializable
data class ShelfDto(
    val id: Int,
    val name: String,
    val bookCount: Int = 0
)

fun List<ShelfDto>.toShelf(): List<LibraryBookShelf> =
    this.map(ShelfDto::toShelf)

fun ShelfDto.toShelf(): LibraryBookShelf =
    LibraryBookShelf(this.id, this.name, this.bookCount)

