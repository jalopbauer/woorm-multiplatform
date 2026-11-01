package com.indigo.woorm.home.bookClubs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.indigo.woorm.bookClubs.data.BookClubTextAvatar
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import com.indigo.woorm.common.card.Card
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

@Composable
fun BookClubUpdates(
    modifier: Modifier = Modifier,
    bookClubPreviews: List<BookClubPreview>,
    onClickBookClubCard: (BookClubPreview) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            Text(
                text = "Book club updates",
                style = MaterialTheme.typography.headlineSmall,
                color = AppTheme.colors.neutral.text
            )
            Spacer(Modifier.height(AppTheme.spacing.vertical.componentToComponent))
        }
        items(bookClubPreviews) { bookClub ->
            Card (
                onClick = { onClickBookClubCard(bookClub) }
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = AppTheme.spacing.horizontal.componentToComponent,
                                vertical = AppTheme.spacing.vertical.componentToComponent
                            ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BookClubTextAvatar(
                            bookClub = bookClub,
                            size = Size.Medium,
                        )
                        Spacer(
                            modifier = Modifier.width(AppTheme.spacing.horizontal.default)
                        )
                        Text(
                            text = bookClub.name,
                            color = AppTheme.colors.neutral.text,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(
                        modifier = Modifier.height(AppTheme.spacing.vertical.textToText)
                    )
                    ProgressChips(
                        bookClub = bookClub
                    )
                }
            }
            Spacer(Modifier.height(AppTheme.spacing.vertical.componentToComponent))
        }
    }
}