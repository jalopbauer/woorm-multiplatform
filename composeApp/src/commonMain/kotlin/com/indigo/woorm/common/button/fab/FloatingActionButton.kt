package com.indigo.woorm.common.button.fab

import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun FloatingActionButton(
    onFabClick: () -> Unit,
    icon: AppIcon.Plus,
    contentDescription: String
) {
    FloatingActionButton(
        onClick = onFabClick,
        containerColor = AppTheme.colors.neutral.primary
    ) {
        Icon(
            icon = icon,
            contentDescription = contentDescription,
            tint = AppTheme.colors.neutral.background
        )
    }
}