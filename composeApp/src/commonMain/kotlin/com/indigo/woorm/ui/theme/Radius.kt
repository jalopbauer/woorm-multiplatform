package com.indigo.woorm.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Radius(
    val none: Dp,
    val min: Dp,
    val xs: Dp,
    val s: Dp,
    val m: Dp,
    val l: Dp,
    val xl: Dp,
    val xxl: Dp
)

val StandardRadius = Radius(
    none = 0.dp,
    min = 4.dp,
    xs = 8.dp,
    s = 16.dp,
    m = 20.dp,
    l = 30.dp,
    xl = 40.dp,
    xxl = 60.dp
)

val LocalCustomRadius = staticCompositionLocalOf { StandardRadius }