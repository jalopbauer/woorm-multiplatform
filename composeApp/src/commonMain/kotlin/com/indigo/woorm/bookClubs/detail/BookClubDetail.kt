package com.indigo.woorm.bookClubs.detail

import com.indigo.woorm.bookClubs.data.BookClub
import com.indigo.woorm.bookClubs.data.BookClubColor
import com.indigo.woorm.books.BookPreview
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import kotlinx.serialization.Serializable

@Serializable
data class BookClubDetail(
    val id: String,
    override val name: String,
    override val color: BookClubColor,
    val currentBook: BookPreview?,
    val books: List<BookPreview>,
    val readingProgress: Map<LibraryBookStatusTypeDTO, Int>,
    val currentUserStatus: LibraryBookStatusTypeDTO? = null,
    val invite: Invite?,
    val members: List<Member> = emptyList(),
    val memberBookStatuses: List<MemberBookStatus> = emptyList(),
): BookClub

@Serializable
data class Member(
    val userId: String,
    val username: String,
    val isAdmin: Boolean,
)

@Serializable
data class MemberBookStatus(
    val userId: String,
    val username: String,
    val status: LibraryBookStatusTypeDTO,
    val rating: Double? = null,
    val comment: String? = null,
)

@Serializable
data class Invite(
    val id: Int
)

@Serializable
data class InviteDetail(
    val id: Int,
    val name: String
)

