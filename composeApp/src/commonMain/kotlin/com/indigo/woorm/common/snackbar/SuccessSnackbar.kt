package com.indigo.woorm.common.snackbar

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun SuccessSnackbar(data: SnackbarData) {
    Snackbar(
        snackbarData = data,
        containerColor = AppTheme.colors.success.stroke,
        contentColor = AppTheme.colors.success.primary,
        dismissActionContentColor = AppTheme.colors.success.primary
    )
}