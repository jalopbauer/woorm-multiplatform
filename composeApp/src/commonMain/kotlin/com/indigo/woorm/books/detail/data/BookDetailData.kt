package com.indigo.woorm.books.detail.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookDetailData(book: BookDetail) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default)
    ) {
        BookDetailAuthors(
            book = book
        )
        if (book.tags.isNotEmpty()) {
            BookDetailTagsCarrousel(
                book = book,
                onClickShowAllTags = {}
            )
        }
        BookDetailLanguagePagesPair(book = book)
        BookDetailIsbn(book = book)
        BookDetailAbstract(book = book)
    }
}