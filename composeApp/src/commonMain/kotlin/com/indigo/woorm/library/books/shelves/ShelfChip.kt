package com.indigo.woorm.library.books.shelves

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.chip.Chip

@Composable
fun <T: Shelf> ShelfChip(
    shelf: T
) {
    Row(
        modifier = Modifier
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Chip(
            label = shelf.name
        )
    }
}