package com.indigo.woorm.shelves.composables.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun ShelfList(
    shelves: List<LibraryBookShelf>,
    onEdit: (LibraryBookShelf) -> Unit,
    onDelete: (LibraryBookShelf) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.horizontal.default)
    ) {
        items(shelves) { shelf ->
            ShelfItem(
                shelf = shelf,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}