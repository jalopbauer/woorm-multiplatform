package com.indigo.woorm.library.shelves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.book.cover.BookCover
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.ui.theme.AppTheme

private val ShelfBookCoverHeight = 166.dp
private val ShelfBookAppendingWidth = 48.dp

@Composable
fun ShelfBookStrip(
    books: List<LibraryBookPreview>,
    isAppending: Boolean,
    onCoverClick: (LibraryBookPreview) -> Unit,
    onLoadMore: () -> Unit,
) {
    val listState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= (totalItemsCount - 4) && totalItemsCount > 0
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.horizontal.default),
    ) {
        items(books) { book ->
            BookCover(
                modifier = Modifier.height(ShelfBookCoverHeight),
                book = book,
                onCoverClick = onCoverClick
            )
        }
        if (isAppending) {
            item {
                Box(
                    modifier = Modifier
                        .width(ShelfBookAppendingWidth)
                        .height(ShelfBookCoverHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    Loading(
                        modifier = Modifier.size(ShelfBookAppendingWidth)
                    )
                }
            }
        }
    }
}
