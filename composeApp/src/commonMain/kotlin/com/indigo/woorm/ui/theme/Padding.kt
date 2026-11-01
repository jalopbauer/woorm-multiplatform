package com.indigo.woorm.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Padding(
    val xs: Dp,
    val s: Dp,
    val m: Dp,
    val l: Dp
)

val StandardPadding = Padding(
    xs = 8.dp,
    s = 16.dp,
    m = 24.dp,
    l = 32.dp
)

val LocalCustomPadding = staticCompositionLocalOf { StandardPadding }