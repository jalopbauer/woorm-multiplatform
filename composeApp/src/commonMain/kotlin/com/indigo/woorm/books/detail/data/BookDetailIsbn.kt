package com.indigo.woorm.books.detail.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.common.chip.Chip

@Composable
fun BookDetailIsbn(book: BookDetail) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        label = "ISBN: ${book.isbn?.takeIf { it != "" } ?: "N/A"}",
        horizontalArrangement = Arrangement.Center
    )
}