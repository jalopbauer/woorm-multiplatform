package com.indigo.woorm.shelves.composables.list

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
import androidx.compose.ui.text.style.TextOverflow
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun ShelfItem(
    shelf: LibraryBookShelf,
    onEdit: (LibraryBookShelf) -> Unit,
    onDelete: (LibraryBookShelf) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.spacing.horizontal.screenMobile
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = shelf.name,
            color = AppTheme.colors.neutral.text,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(AppTheme.spacing.horizontal.default))
        IconButton(
            variant = Variant.Ghost,
            style = Style.Neutral,
            onClick = { onEdit(shelf) },
            icon = AppIcon.Pencil,
            contentDescription = "Edit shelf"
        )
        Spacer(modifier = Modifier.width(AppTheme.spacing.horizontal.componentToComponent))
        IconButton(
            variant = Variant.Ghost,
            style = Style.Neutral,
            onClick = { onDelete(shelf) },
            icon = AppIcon.Trash2,
            contentDescription = "Delete shelf"
        )
    }

}
