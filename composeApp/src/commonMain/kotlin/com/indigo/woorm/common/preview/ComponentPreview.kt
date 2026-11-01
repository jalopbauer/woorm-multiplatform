package com.indigo.woorm.common.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun ComponentPreview(content: @Composable ColumnScope.() -> Unit,) {
    MaterialTheme {
        Column(
            modifier = Modifier.background(AppTheme.colors.neutral.background)
        ) {
            content()
        }
    }
}