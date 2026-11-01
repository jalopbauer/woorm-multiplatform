package com.indigo.woorm.library.books.status

import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

sealed interface LibraryBookStatus {
    val id: Int?
    val createdAt: LocalDateTime
    val startDate: LocalDate?

    val type: LibraryBookStatusTypeDTO

    data class InProgress (
        override val id: Int? = null,
        override val startDate: LocalDate?,
        override val createdAt: LocalDateTime,
    ) : LibraryBookStatus {
        override val type = LibraryBookStatusTypeDTO.IN_PROGRESS
    }

    data class Read (
        override val id: Int? = null,
        override val startDate: LocalDate?,
        val userRating: Double?,
        val endDate: LocalDate?,
        override val createdAt: LocalDateTime
    ) : LibraryBookStatus {
        override val type = LibraryBookStatusTypeDTO.READ
    }

    data class Dnf (
        override val id: Int? = null,
        override val startDate: LocalDate?,
        override val createdAt: LocalDateTime
    ) : LibraryBookStatus {
        override val type = LibraryBookStatusTypeDTO.DNF
    }
}