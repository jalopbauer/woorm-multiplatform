package com.indigo.woorm.common.book

import kotlinx.serialization.Serializable

interface Book {
    val id: Int
    val coverUrl: String
    val title: String
    val authors: List<Author>

    fun authors(): String = authors.joinToString(", ", transform = Author::name )
}


@Serializable
data class Language(
    val id: Int,
    val name: String
)

@Serializable
data class Author(
    val id: Int,
    val name: String
)

@Serializable
data class Tag(
    val id: Int,
    val name: String
)