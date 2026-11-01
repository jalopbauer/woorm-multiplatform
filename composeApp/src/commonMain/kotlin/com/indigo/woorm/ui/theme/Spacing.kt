package com.indigo.woorm.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class VerticalSpacing(
    val default: Dp,
    val textToText: Dp,
    val componentToComponent: Dp,
    val sectionToSection: Dp
)

@Immutable
data class HorizontalSpacing(
    val componentToComponent: Dp,
    val default: Dp,
    val screenMobile: Dp
) {
    val none: Dp = 0.dp
}

@Immutable
data class Spacing(
    val vertical: VerticalSpacing,
    val horizontal: HorizontalSpacing
)

val StandardSpacing = Spacing(
    vertical = VerticalSpacing(
        default = 16.dp,
        textToText = 4.dp,
        componentToComponent = 8.dp,
        sectionToSection = 24.dp
    ),
    horizontal = HorizontalSpacing(
        componentToComponent = 8.dp,
        default = 16.dp,
        screenMobile = 24.dp
    )
)

val LocalCustomSpacing = staticCompositionLocalOf { StandardSpacing }