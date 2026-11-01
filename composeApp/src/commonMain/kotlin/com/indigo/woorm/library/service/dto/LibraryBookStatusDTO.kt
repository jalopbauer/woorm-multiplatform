package com.indigo.woorm.library.service.dto

import com.indigo.woorm.library.books.status.LibraryBookStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Serializable
sealed interface LibraryBookStatusDTO {
    val id: Int?
    val createdAt: String

    @Serializable
    @SerialName("IN_PROGRESS")
    data class InProgressDTO(
        override val id: Int? = null,
        val startDate: String?,
        override val createdAt: String,
    ) : LibraryBookStatusDTO

    @Serializable
    @SerialName("READ")
    data class ReadDTO(
        override val id: Int? = null,
        val startDate: String?,
        val userRating: Double?,
        val endDate: String?,
        override val createdAt: String,
    ) : LibraryBookStatusDTO

    @Serializable
    @SerialName("DNF")
    data class DnfDTO(
        override val id: Int? = null,
        val startDate: String?,
        override val createdAt: String,
    ) : LibraryBookStatusDTO
}

val libraryBookSerializersModule = SerializersModule {
    polymorphic(LibraryBookStatusDTO::class) {
        subclass(LibraryBookStatusDTO.InProgressDTO::class)
        subclass(LibraryBookStatusDTO.ReadDTO::class)
        subclass(LibraryBookStatusDTO.DnfDTO::class)
    }
}

fun LibraryBookStatusDTO.toLibraryBookStatus(): LibraryBookStatus =
    when (this) {
        is LibraryBookStatusDTO.DnfDTO -> {
            LibraryBookStatus.Dnf(
                id = this.id,
                startDate = this.startDate?.let { LocalDate.parse(it) },
                createdAt = LocalDateTime.parse(this.createdAt),
            )
        }
        is LibraryBookStatusDTO.InProgressDTO -> {
            LibraryBookStatus.InProgress(
                id = this.id,
                startDate = this.startDate?.let { LocalDate.parse(it) },
                createdAt = LocalDateTime.parse(this.createdAt),
            )
        }
        is LibraryBookStatusDTO.ReadDTO -> {
            LibraryBookStatus.Read(
                id = this.id,
                startDate = this.startDate?.let { LocalDate.parse(it) },
                userRating = this.userRating,
                endDate = this.endDate?.let { LocalDate.parse(it) },
                createdAt = LocalDateTime.parse(this.createdAt),
            )
        }
    }