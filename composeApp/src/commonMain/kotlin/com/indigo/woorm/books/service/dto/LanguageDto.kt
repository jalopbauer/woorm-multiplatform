package com.indigo.woorm.books.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class LanguageDto(
    val id: Int,
    val name: String
)