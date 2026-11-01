package com.indigo.woorm.common.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Shape
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import com.indigo.woorm.ui.theme.shape

data class ButtonStyle(
    val variant: Variant,
    val style: Style,
    val shape: Shape,
) {
    @Composable
    fun composeShape() = shape(shape)

    @Composable
    fun domain() = when (style) {
        is Style.Primary -> AppTheme.colors.primary
        is Style.Danger -> AppTheme.colors.error
        is Style.Neutral -> AppTheme.colors.neutral
    }

    @Composable
    fun background() = when (variant) {
        Variant.Primary -> domain().primary
        Variant.Ghost -> Color.Transparent
        Variant.Surface -> when (style) {
            is Style.Primary,
            is Style.Danger -> domain().stroke
            is Style.Neutral -> domain().surface
        }
        Variant.Outlined -> domain().background
    }

    @Composable
    fun text() = when (variant) {
        Variant.Primary -> domain().background
        Variant.Ghost -> when (style) {
            is Style.Primary,
            is Style.Danger -> domain().primary
            is Style.Neutral -> domain().text
        }
        Variant.Surface -> domain().text
        Variant.Outlined -> domain().text
    }

    @Composable
    fun border() = when (variant) {
        Variant.Outlined ->
            BorderStroke(width = AppTheme.stroke.s, color = AppTheme.colors.neutral.primary)
        else -> null
    }
}