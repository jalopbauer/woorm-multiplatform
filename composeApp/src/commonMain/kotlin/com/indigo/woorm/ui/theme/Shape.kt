package com.indigo.woorm.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable

sealed interface Shape

data object Square : Shape
data object Rounded : Shape
data object Circle : Shape
data object Min : Shape


@Composable
fun shape(shape: Shape) = when (shape) {
    Square -> RoundedCornerShape(AppTheme.radius.none)
    Rounded -> RoundedCornerShape(AppTheme.radius.s)
    Circle -> RoundedCornerShape(AppTheme.radius.l)
    Min -> RoundedCornerShape(AppTheme.radius.min)
}