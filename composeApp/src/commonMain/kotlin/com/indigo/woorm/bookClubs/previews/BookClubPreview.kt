package com.indigo.woorm.bookClubs.previews

import com.indigo.woorm.bookClubs.data.BookClub
import com.indigo.woorm.bookClubs.data.BookClubColor
import com.indigo.woorm.books.BookPreview
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import kotlinx.serialization.Serializable

@Serializable
data class BookClubPreview(
    val id: String,
    override val name: String,
    override val color: BookClubColor,
    val currentBook: BookPreview?,
    val readingProgress: Map<LibraryBookStatusTypeDTO, Int>,
) : BookClub {
    val isCurrentBookSet = this.currentBook != null
}
