package com.indigo.woorm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun WoormTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val materialColorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()

    val colors = if (useDarkTheme) DarkDomainColors else LightDomainColors
    val spacing = StandardSpacing
    val radius = StandardRadius
    val padding = StandardPadding
    val stroke = StandardStroke

    CompositionLocalProvider(
        LocalDomainColors provides colors,
        LocalCustomSpacing provides spacing,
        LocalCustomRadius provides radius,
        LocalCustomPadding provides padding,
        LocalCustomStroke provides stroke
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content
        )
    }
}

object AppTheme {
    val colors: DomainColors
        @Composable
        get() = LocalDomainColors.current

    val spacing: Spacing
        @Composable
        get() = LocalCustomSpacing.current

    val radius: Radius
        @Composable get() = LocalCustomRadius.current

    val padding: Padding
        @Composable get() = LocalCustomPadding.current

    val stroke: Stroke
        @Composable get() = LocalCustomStroke.current
}