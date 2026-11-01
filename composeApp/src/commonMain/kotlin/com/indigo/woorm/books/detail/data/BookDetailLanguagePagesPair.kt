package com.indigo.woorm.books.detail.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.common.chip.Chip
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookDetailLanguagePagesPair(book: BookDetail) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default)
    ) {
        Chip(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            label = book.language.name,
            horizontalArrangement = Arrangement.Center
        )
        Chip(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            label = "${book.pages} pages",
            horizontalArrangement = Arrangement.Center
        )
    }
}