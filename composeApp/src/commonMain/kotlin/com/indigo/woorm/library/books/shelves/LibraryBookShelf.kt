package com.indigo.woorm.library.books.shelves

import kotlinx.serialization.Serializable


interface Shelf {
    val id: Int
    val name: String
}


@Serializable
data class LibraryBookShelf (
    override val id: Int,
    override val name: String,
    val bookCount: Int = 0
) : Shelf

@Serializable
data class LibraryShelf(
    override val id: Int,
    override val name: String,
    val selected: Boolean
): Shelf