package com.indigo.woorm.books.detail.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookDetailAuthors(book: BookDetail) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.textToText)
    ) {
        Text(
            text = "by",
            style = MaterialTheme.typography.titleLarge,
            color = AppTheme.colors.neutral.text,
        )
        Text(
            text = book.authors(),
            style = MaterialTheme.typography.headlineMedium,

            color = AppTheme.colors.neutral.text,
        )
    }
}