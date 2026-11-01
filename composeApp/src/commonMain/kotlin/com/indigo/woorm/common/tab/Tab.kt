package com.indigo.woorm.common.tab

import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Tab(selected: Boolean, onClick: () -> Unit, text: String) {
    Tab(
        selected = selected,
        onClick = onClick,
        text = { Text(text) }
    )
}