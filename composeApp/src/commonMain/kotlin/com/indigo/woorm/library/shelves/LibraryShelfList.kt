package com.indigo.woorm.library.shelves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.library.ShelfBooksUiState
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun LibraryShelfList(
    shelves: List<LibraryBookShelf>,
    expandedShelves: Map<Int, ShelfBooksUiState>,
    onShelfClick: (LibraryBookShelf) -> Unit,
    onCoverClick: (LibraryBookPreview) -> Unit,
    onLoadMore: (LibraryBookShelf) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.componentToComponent),
    ) {
        items(shelves, key = { it.id }) { shelf ->
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.componentToComponent),
            ) {
                LibraryShelfItem(
                    shelf = shelf,
                    expanded = expandedShelves.containsKey(shelf.id),
                    onClick = onShelfClick,
                )
                when (val state = expandedShelves[shelf.id]) {
                    is ShelfBooksUiState.Loading -> Loading(
                        modifier = Modifier.size(48.dp)
                    )

                    is ShelfBooksUiState.Error -> {

                    }

                    is ShelfBooksUiState.Success -> ShelfBookStrip(
                        books = state.books,
                        isAppending = state.isLoadingMore,
                        onCoverClick = onCoverClick,
                        onLoadMore = { onLoadMore(shelf) },
                    )

                    null -> {

                    }
                }
            }
        }
    }
}
