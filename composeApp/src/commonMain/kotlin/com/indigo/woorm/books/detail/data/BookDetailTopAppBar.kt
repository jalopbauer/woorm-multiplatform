package com.indigo.woorm.books.detail.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun BookDetailTopAppBar(
    book: BookDetail,
    isScrolled: Boolean,
    onClickBackIconButton: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(
                when {
                    isScrolled -> AppTheme.colors.neutral.stroke
                    else -> Color.Transparent
                }
            )
            .statusBarsPadding()
            .padding(
                all =  AppTheme.padding.xs
            ),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            variant = when {
                isScrolled -> Variant.Surface
                else -> Variant.Ghost
            },
            style = Style.Neutral,
            onClick = onClickBackIconButton,
            icon = AppIcon.ArrowLeft,
            contentDescription = "Back"
        )
        if (isScrolled) {
            Text(
                text = book.title,
                color = AppTheme.colors.neutral.text,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}