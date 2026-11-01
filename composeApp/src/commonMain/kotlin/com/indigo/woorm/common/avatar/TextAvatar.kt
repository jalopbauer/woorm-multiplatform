package com.indigo.woorm.common.avatar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.indigo.woorm.ui.theme.AvatarSet
import com.indigo.woorm.ui.theme.Size

@Composable
fun TextAvatar(
    size: Size,
    avatarSet: AvatarSet,
    name: String
) {
    val typography = when (size) {
        Size.Large -> MaterialTheme.typography.displayMedium
        Size.Medium -> MaterialTheme.typography.headlineSmall
        Size.Small -> MaterialTheme.typography.titleSmall
    }
    Avatar(
        size = size,
        avatarSet = avatarSet,
    ) { color ->
        Text(
            text = name,
            color = color,
            style = typography,
        )
    }
}