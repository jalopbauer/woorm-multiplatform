package com.indigo.woorm.library.service.dto

import com.indigo.woorm.common.icon.AppIcon

enum class LibraryBookStatusTypeDTO {
    IN_PROGRESS,
    READ,
    DNF
}

fun LibraryBookStatusTypeDTO.icon(): AppIcon =
    when (this) {
        LibraryBookStatusTypeDTO.IN_PROGRESS -> AppIcon.BookOpen
        LibraryBookStatusTypeDTO.READ -> AppIcon.BookCheck
        LibraryBookStatusTypeDTO.DNF -> AppIcon.BookX
    }

fun LibraryBookStatusTypeDTO.displayName(): String = when (this) {
    LibraryBookStatusTypeDTO.IN_PROGRESS -> "In progress"
    LibraryBookStatusTypeDTO.READ -> "Read"
    LibraryBookStatusTypeDTO.DNF -> "Dnf"
}