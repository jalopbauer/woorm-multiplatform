package com.indigo.woorm.bookClubs.data

import androidx.compose.runtime.Composable
import com.indigo.woorm.common.avatar.TextAvatar
import com.indigo.woorm.ui.theme.Size

@Composable
fun BookClubTextAvatar(
    bookClub: BookClub,
    size: Size,
) {
    val name =
        bookClub.name.split(" ").take(2).map { it.getOrNull(0) ?: "" }.joinToString("")
    val avatarSet = bookClubColorToAvatarColor(bookClub.color)

    TextAvatar(
        size = size,
        avatarSet = avatarSet,
        name = name
    )
}