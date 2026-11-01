package com.indigo.woorm.common.button.icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun IconButton(
    variant: Variant,
    style: Style,
    onClick: () -> Unit,
    icon: AppIcon,
    contentDescription: String
) {
    val domain = when (style) {
        is Style.Primary -> AppTheme.colors.primary
        is Style.Danger -> AppTheme.colors.error
        is Style.Neutral -> AppTheme.colors.neutral
    }

    val background = when (variant) {
        Variant.Primary -> domain.primary
        Variant.Ghost -> domain.background
        Variant.Surface -> when (style) {
            is Style.Primary,
            is Style.Danger -> domain.stroke

            is Style.Neutral -> domain.stroke
        }

        Variant.Outlined -> domain.background
    }

    val text = when (variant) {
        Variant.Primary -> domain.background
        Variant.Ghost -> when (style) {
            is Style.Primary,
            is Style.Danger -> domain.primary
            is Style.Neutral -> domain.text
        }

        Variant.Surface -> domain.text
        Variant.Outlined -> domain.text
    }

    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = background,
            contentColor = text,
        )
    ) {
        Icon(
            icon = icon,
            contentDescription = contentDescription,
        )
    }
}