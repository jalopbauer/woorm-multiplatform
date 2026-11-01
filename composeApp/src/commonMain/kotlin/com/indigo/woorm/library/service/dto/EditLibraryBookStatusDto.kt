package com.indigo.woorm.library.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditLibraryBookStatusDto(
    val libraryBookStatusType: LibraryBookStatusTypeDTO? = null,
    val startDate: String? = null,
    val userRating: Double? = null,
    val endDate: String? = null,
)