package com.indigo.woorm.common.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ScreenPreview(content: @Composable () -> Unit,) {
    MaterialTheme {
        content()
    }
}