package com.indigo.woorm.library.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateLibraryBookCommentDto(
    val comment: String,
)
