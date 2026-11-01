package com.indigo.woorm.bookClubs.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.indigo.woorm.bookClubs.data.BookClubTextAvatar
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun BookDetailTopBar(
    bookClubDetail: BookClubDetail,
    onClickHeader: () -> Unit,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.neutral.stroke)
            .statusBarsPadding()
            .padding(all = AppTheme.padding.xs)
            .clickable(
                onClick = onClickHeader
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                variant = Variant.Surface,
                style = Style.Neutral,
                onClick = onClickBack,
                icon = AppIcon.ArrowLeft,
                contentDescription = "Back"
            )
            BookClubTextAvatar(
                bookClub = bookClubDetail,
                size = Size.Medium
            )
            Spacer(
                modifier = Modifier.width(AppTheme.spacing.horizontal.default)
            )
            Text(
                text = bookClubDetail.name,
                color = AppTheme.colors.neutral.text,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            variant = Variant.Surface,
            style = Style.Neutral,
            onClick = onClickShare,
            icon = AppIcon.Share2,
            contentDescription = "Back"
        )
    }
}
