package com.indigo.woorm.library.filters

import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO

/**
 * UI filter for the library screen. Adds [TBR] (To Be Read) on top of the three
 * persisted statuses. TBR is not a stored status — it selects library books that
 * have no current status. "All" is represented by `null` at the call site.
 */
enum class LibraryBookStatusFilter {
    TBR,
    READ,
    IN_PROGRESS,
    DNF;

    /** Maps this filter to the persisted status DTO sent as the `status` query param. */
    fun toStatusDto(): LibraryBookStatusTypeDTO? = when (this) {
        TBR -> null
        READ -> LibraryBookStatusTypeDTO.READ
        IN_PROGRESS -> LibraryBookStatusTypeDTO.IN_PROGRESS
        DNF -> LibraryBookStatusTypeDTO.DNF
    }

    /** Whether this filter should be sent as `?tbr=true` instead of `?status=...`. */
    val isTbr: Boolean get() = this == TBR
}

fun LibraryBookStatusFilter.icon(): AppIcon = when (this) {
    LibraryBookStatusFilter.TBR -> AppIcon.Heart
    LibraryBookStatusFilter.READ -> AppIcon.BookCheck
    LibraryBookStatusFilter.IN_PROGRESS -> AppIcon.BookOpen
    LibraryBookStatusFilter.DNF -> AppIcon.BookX
}

fun LibraryBookStatusFilter.displayName(): String = when (this) {
    LibraryBookStatusFilter.TBR -> "TBR"
    LibraryBookStatusFilter.READ -> "Read"
    LibraryBookStatusFilter.IN_PROGRESS -> "In progress"
    LibraryBookStatusFilter.DNF -> "Dnf"
}
