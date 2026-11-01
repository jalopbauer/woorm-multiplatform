package com.indigo.woorm.bookClubs.data

import androidx.compose.runtime.Composable
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.AvatarSet

enum class BookClubColor {
    RED,
    BLUE,
    GREEN,
    YELLOW,
}

@Composable
fun bookClubColorToAvatarColor(color: BookClubColor): AvatarSet = when (color) {
    BookClubColor.RED -> AppTheme.colors.avatars.red
    BookClubColor.BLUE -> AppTheme.colors.avatars.blue
    BookClubColor.GREEN -> AppTheme.colors.avatars.green
    BookClubColor.YELLOW -> AppTheme.colors.avatars.yellow
}