package com.indigo.woorm.bookClubs.detail.membersBookStatusList.card.header

import androidx.compose.runtime.Composable
import com.indigo.woorm.bookClubs.data.BookClub
import com.indigo.woorm.bookClubs.data.bookClubColorToAvatarColor
import com.indigo.woorm.common.avatar.IconAvatar
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.Size

@Composable
fun MemberAvatar(
    bookClub: BookClub,
    size: Size,
) {
    val avatarSet = bookClubColorToAvatarColor(bookClub.color)
    IconAvatar(
        size = size,
        avatarSet = avatarSet,
        icon = AppIcon.Worm,
        contentDescription = "Member",
    )
}