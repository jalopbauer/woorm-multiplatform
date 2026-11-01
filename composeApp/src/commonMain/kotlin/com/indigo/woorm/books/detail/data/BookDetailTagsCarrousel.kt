package com.indigo.woorm.books.detail.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.chip.Chip
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant


@Composable
fun BookDetailTagsCarrousel(
    book: BookDetail,
    onClickShowAllTags: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default)
    ) {
        item {
            Text(
                text = "Tags",
                style = MaterialTheme.typography.labelLarge,
                color = AppTheme.colors.neutral.text,
            )
        }
        items(book.tags) { tag ->
            Chip(
                label = tag.name,
            )
        }
        if (book.tags.size >= 5) {
            item {
                Button(
                    onClick = onClickShowAllTags,
                    variant = Variant.Surface,
                    style = Style.Neutral,
                    label = "All tags",
                    trailingIcon = AppIcon.ChevronDown,
                    contentDescription = "Show all tags",
                    shape = Rounded
                )
            }
        }
    }
}