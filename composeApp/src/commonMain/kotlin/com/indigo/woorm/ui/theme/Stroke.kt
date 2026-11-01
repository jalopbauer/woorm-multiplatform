package com.indigo.woorm.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Stroke(
    val xs: Dp,
    val s: Dp
)


val StandardStroke = Stroke(
    xs = 1.dp,
    s = 2.dp
)
val LocalCustomStroke = staticCompositionLocalOf { StandardStroke }