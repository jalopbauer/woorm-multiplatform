package com.indigo.woorm.home.inProgress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.book.cover.BookCover
import com.indigo.woorm.common.button.pair.ButtonPair
import com.indigo.woorm.common.card.Card
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookInProgressCard(
    book: LibraryBookPreview,
    onClickBookDetailCard: (LibraryBookPreview) -> Unit,
    onDnfClick: (LibraryBookPreview) -> Unit,
    onReadClick: (LibraryBookPreview) -> Unit
) {
    Card(
        onClick = { onClickBookDetailCard(book) }
    ) {
        Row(modifier = Modifier.height(182.dp)) {
            BookCover(
                modifier = Modifier.fillMaxHeight(),
                radius = false,
                book = book
            )
            Column(
                modifier = Modifier
                    .padding(AppTheme.padding.xs)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = AppTheme.colors.neutral.text,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = book.authors(),
                        style = MaterialTheme.typography.titleMedium,
                        color = AppTheme.colors.neutral.highlight,
                    )
                }

                ButtonPair(
                    onLeftClick = { onDnfClick(book)  },
                    leftLabel = "Dnf",
                    leftIcon = AppIcon.BookX,
                    leftContentDescription = "Add book to Dnf",
                    onRightClick = { onReadClick(book)  },
                    rightLabel = "Read",
                    rightIcon = AppIcon.BookCheck,
                    rightContentDescription = "Book read",
                )
            }
        }
    }
}

