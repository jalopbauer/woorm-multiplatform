package com.indigo.woorm.bookClubs.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import com.indigo.woorm.common.loading.Loading
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookClubsScreen(
    onBookClubClick: (BookClubPreview) -> Unit,
    viewModel: BookClubsViewModel = koinViewModel()
) {
    val bookClubsState by viewModel.bookClubsState.collectAsState()

    when (bookClubsState) {
        is BookClubsState.Loading -> Loading(
            modifier = Modifier.fillMaxSize()
        )
        is BookClubsState.Success -> BookClubsContent(
            bookClubPreviews = (bookClubsState as BookClubsState.Success).bookClubs,
            onClickCreateBookClub = { bookClubData ->
                viewModel.createBookClub(bookClubData)
            },
            onBookClubClick = onBookClubClick
        )
    }
}