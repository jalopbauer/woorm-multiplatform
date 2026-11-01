package com.indigo.woorm.common.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.AvatarSet
import com.indigo.woorm.ui.theme.Size


@Composable
fun Avatar(
    size: Size,
    avatarSet: AvatarSet,
    content: @Composable (color: Color) -> Unit,
) {
    val backgroundSize = when (size) {
        Size.Large -> 96.dp
        Size.Medium -> 48.dp
        Size.Small -> 40.dp
    }
    Box(
        modifier = Modifier
            .size(backgroundSize)
            .clip(RoundedCornerShape(AppTheme.radius.s))
            .background(avatarSet.club),
        contentAlignment = Alignment.Center,
    ) {
        content(avatarSet.text)
    }
}