package com.indigo.woorm.common.book.cover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.book.Book
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun <T: Book> BookCoverGrid(
    books: List<T>,
    onCoverClick: (T) -> Unit,
    isAppending: Boolean = false,
    onLoadMore: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val listState = rememberLazyGridState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= (totalItemsCount - 4) && totalItemsCount > 0
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }

    LazyVerticalGrid(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.horizontal.default)
    ) {
        items(books) { bookPreview ->
            BookCover(
                book = bookPreview,
                onCoverClick = onCoverClick
            )
        }
        if (isAppending) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Loading(
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
    }
}