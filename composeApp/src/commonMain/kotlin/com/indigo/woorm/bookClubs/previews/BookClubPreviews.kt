package com.indigo.woorm.bookClubs.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookClubPreviews(
    bookClubPreviews: List<BookClubPreview>,
    onClick: (BookClubPreview) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.horizontal.default)
    ) {
        items(bookClubPreviews) { bookClub ->
            BookClubCard(
                bookClub = bookClub,
                onClick = onClick
            )
        }
    }
}