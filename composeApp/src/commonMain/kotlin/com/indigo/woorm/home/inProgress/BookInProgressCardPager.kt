package com.indigo.woorm.home.inProgress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookInProgressCardPager(
    modifier: Modifier = Modifier,
    libraryBooks: List<LibraryBookPreview>,
    onClickBookDetailCard: (LibraryBookPreview) -> Unit,
    onDnfClick: (LibraryBookPreview) -> Unit,
    onReadClick: (LibraryBookPreview) -> Unit
) {
    if (libraryBooks.isNotEmpty()) {
        val state = rememberPagerState { libraryBooks.size }
        val screenPadding = AppTheme.spacing.horizontal.screenMobile
        Column (
            modifier = modifier
        ) {
            Text(
                modifier = Modifier.padding(horizontal = screenPadding),
                text = "Currently reading",
                style = MaterialTheme.typography.headlineSmall,
                color = AppTheme.colors.neutral.text
            )
            Spacer(Modifier.height(AppTheme.spacing.vertical.componentToComponent))
            HorizontalPager(
                state = state,
                key = { page -> libraryBooks[page].id },
                pageSpacing = AppTheme.spacing.vertical.componentToComponent,
                contentPadding = PaddingValues(horizontal = screenPadding)
            ) { page ->
                val book = libraryBooks[page]
                BookInProgressCard(
                    onClickBookDetailCard = onClickBookDetailCard,
                    book = book,
                    onDnfClick = onDnfClick,
                    onReadClick = onReadClick
                )
            }
        }
    }
}