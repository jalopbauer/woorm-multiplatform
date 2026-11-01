package com.indigo.woorm.common.input

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun textInputColors() = OutlinedTextFieldDefaults.colors(
    cursorColor = AppTheme.colors.neutral.text,
    focusedContainerColor = AppTheme.colors.neutral.surface,
    unfocusedContainerColor = AppTheme.colors.neutral.surface,
    focusedBorderColor = AppTheme.colors.neutral.stroke,
    unfocusedBorderColor = AppTheme.colors.neutral.stroke,
    focusedTextColor = AppTheme.colors.neutral.text,
    unfocusedTextColor = AppTheme.colors.neutral.text,
    selectionColors = TextSelectionColors(
        handleColor = AppTheme.colors.neutral.primary,
        backgroundColor = AppTheme.colors.neutral.primary.copy(alpha = 0.4f)
    )
)