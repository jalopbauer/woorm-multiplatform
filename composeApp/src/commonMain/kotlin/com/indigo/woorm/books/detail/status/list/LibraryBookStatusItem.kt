package com.indigo.woorm.books.detail.status.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.datetime.number
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.library.books.status.LibraryBookStatus
import com.indigo.woorm.library.service.dto.displayName
import com.indigo.woorm.library.service.dto.icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun LibraryBookStatusItem(
    status: LibraryBookStatus,
    onClickDelete: () -> Unit,
) {
    val type = status.type
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            type.icon(),
            contentDescription = type.displayName()
        )
        Spacer(
            modifier = Modifier.width(AppTheme.spacing.vertical.componentToComponent)
        )
        Text(
            text = type.displayName(),
            color = AppTheme.colors.neutral.text
        )
        val statusDate = status.createdAt
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            // java.time's DateTimeFormatter("MM/yyyy") equivalent: zero-padded
            // month number / full year (e.g. "03/2025").
            text = "${statusDate.date.month.number.toString().padStart(2, '0')}/${statusDate.date.year}",
            textAlign = TextAlign.Right,
            color = AppTheme.colors.neutral.text

        )
        Spacer(
            modifier = Modifier.width(AppTheme.spacing.vertical.textToText)
        )
        IconButton(
            variant = Variant.Ghost,
            style = Style.Neutral,
            onClick = onClickDelete,
            icon = AppIcon.Trash2,
            contentDescription = "Delete book status"
        )
    }
}