package com.indigo.woorm.common.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun Card(
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(AppTheme.radius.m)
    val colors = CardDefaults.cardColors(
        containerColor = AppTheme.colors.neutral.surface
    )
    val border = BorderStroke(
        width = AppTheme.stroke.xs,
        color = AppTheme.colors.neutral.stroke
    )
    if (onClick == null) {
        Card(
            shape = shape,
            colors = colors,
            border = border
        ) {
            content()
        }
    } else {
        Card(
            onClick = onClick,
            shape = shape,
            colors = colors,
            border = border
        ) {
            content()
        }
    }
}