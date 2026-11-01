package com.indigo.woorm.books.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetBooksDto(
    val id: Int,
    val title: String,
    val coverUrl: String,
)