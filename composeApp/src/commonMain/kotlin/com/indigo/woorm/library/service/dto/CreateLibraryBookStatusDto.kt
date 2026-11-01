package com.indigo.woorm.library.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateLibraryBookStatusDto(
    val libraryBookStatusType: LibraryBookStatusTypeDTO,
    val endDate: String? = null
)
