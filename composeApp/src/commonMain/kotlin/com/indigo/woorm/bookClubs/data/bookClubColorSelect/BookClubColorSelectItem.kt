package com.indigo.woorm.bookClubs.data.bookClubColorSelect

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.indigo.woorm.bookClubs.data.BookClubColor
import com.indigo.woorm.bookClubs.data.bookClubColorToAvatarColor
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookClubColorSelectItem(
    expected: BookClubColor,
    current: BookClubColor,
    onClickColor: (BookClubColor) -> Unit
) {
    val color = bookClubColorToAvatarColor(expected)
    Box(
        modifier =
            Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(AppTheme.radius.xs))
                .background(color.club)
                .clickable(
                    role = Role.ValuePicker,
                    onClick = {
                        onClickColor(expected)
                    },
                ),
        contentAlignment = Alignment.Center
    ) {
        if (current == expected) {
            Icon(
                AppIcon.Check,
                contentDescription = "Selected",
                tint = color.text
            )
        }
    }
}