package com.indigo.woorm.common.avatar

import androidx.compose.runtime.Composable
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AvatarSet
import com.indigo.woorm.ui.theme.Size

@Composable
fun IconAvatar(
    icon: AppIcon,
    contentDescription: String,
    avatarSet: AvatarSet,
    size: Size
) {
    Avatar(
        size = size,
        avatarSet = avatarSet,
    ) { color ->
        Icon(
            icon = icon,
            contentDescription = contentDescription,
            tint = color,
            size = size
        )
    }
}