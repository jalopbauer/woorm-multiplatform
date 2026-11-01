package com.indigo.woorm.library.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateLibraryShelf(
    val name: String,
    val bookId: Int? = null
)