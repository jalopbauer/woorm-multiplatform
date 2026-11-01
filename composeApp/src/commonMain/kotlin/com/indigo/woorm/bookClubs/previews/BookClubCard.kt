package com.indigo.woorm.bookClubs.previews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.indigo.woorm.bookClubs.data.BookClubTextAvatar
import com.indigo.woorm.common.card.Card
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

@Composable
fun BookClubCard(
    bookClub: BookClubPreview,
    onClick: (BookClubPreview) -> Unit,
) {
    Card(
        onClick = { onClick(bookClub) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = AppTheme.spacing.vertical.default,
                    horizontal = AppTheme.spacing.horizontal.componentToComponent
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BookClubTextAvatar(
                bookClub = bookClub,
                size = Size.Large
            )
            Spacer(
                modifier = Modifier
                    .height(AppTheme.spacing.vertical.componentToComponent)
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = bookClub.name,
                color = AppTheme.colors.neutral.text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}