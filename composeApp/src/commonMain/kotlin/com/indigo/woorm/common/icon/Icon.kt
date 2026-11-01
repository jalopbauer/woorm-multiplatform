package com.indigo.woorm.common.icon

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.unit.dp
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

@Composable
fun Icon(
    icon: AppIcon,
    contentDescription: String,
    tint: Color = AppTheme.colors.neutral.text,
    size: Size = Size.Small,
) {
    val dp = when (size) {
        Size.Large -> 48.dp
        Size.Medium -> 36.dp
        Size.Small -> 24.dp
    }
    androidx.compose.material3.Icon(
        modifier = Modifier.size(dp),
        painter = painterResource(icon.res),
        contentDescription = contentDescription,
        tint = tint,
    )
}