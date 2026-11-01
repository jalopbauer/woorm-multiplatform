package com.indigo.woorm.ui.theme

sealed interface Style {
    object Neutral: Style
    object Primary: Style
    object Danger: Style
}