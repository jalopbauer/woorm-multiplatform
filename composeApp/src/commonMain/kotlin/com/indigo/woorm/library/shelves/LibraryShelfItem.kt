package com.indigo.woorm.library.shelves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun LibraryShelfItem(
    shelf: LibraryBookShelf,
    expanded: Boolean,
    onClick: (LibraryBookShelf) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onClick(shelf) },
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
        Box(
            modifier = Modifier.rotate(if (expanded) 180f else 0f)
        ) {
            Icon(
                icon = AppIcon.ChevronDown,
                contentDescription = if (expanded) "Collapse shelf" else "Expand shelf"
            )
        }
    }
}
