package com.indigo.woorm.library.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateLibraryBookDto(
    val libraryBookStatusType: LibraryBookStatusTypeDTO? = null,
    val endDate: String? = null
)
