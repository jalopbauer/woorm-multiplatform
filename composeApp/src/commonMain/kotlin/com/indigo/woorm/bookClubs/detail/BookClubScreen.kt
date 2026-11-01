package com.indigo.woorm.bookClubs.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.indigo.woorm.common.book.Book
import com.indigo.woorm.common.loading.Loading
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookClubScreen(
    viewModel: BookClubDetailViewModel = koinViewModel(),
    onClickBack: () -> Unit,
    onClickToBook: (Book) -> Unit
) {
    val state by viewModel.bookClub.collectAsState()
    val bookSearchState by viewModel.bookSearchState.collectAsState()
    val isStartingBook by viewModel.isStartingBook.collectAsState()
    val isFinishingBook by viewModel.isFinishingBook.collectAsState()
    LifecycleResumeEffect(Unit) {
        viewModel.bookClub()
        onPauseOrDispose {
        }
    }
    when (state) {
        BookClubState.Loading -> Loading(
            modifier = Modifier.fillMaxSize()
        )
        BookClubState.NotFound -> {

        }
        is BookClubState.Success -> BookClubContent(
            bookClubDetail = (state as BookClubState.Success).bookClub,
            bookSearchState = bookSearchState,
            isStartingBook = isStartingBook,
            isFinishingBook = isFinishingBook,
            onClickBack = onClickBack,
            onClickToBook = onClickToBook,
            onClickRemoveCurrentBook = {
                viewModel.removeCurrentBook()
            },
            onClickSelectCurrentBook = { book ->
                viewModel.setCurrentBook(book)
            },
            startCurrentBook = {
                viewModel.startCurrentBook()
            },
            finishCurrentBook = {
                viewModel.finishCurrentBook()
            },
            ensureInvite = {
                viewModel.ensureInvite()
            },
            onBookSearchStart = {
                viewModel.startBookSearch()
            },
            onBookSearchStop = {
                viewModel.stopBookSearch()
            },
            onBookSearchQueryChange = { query ->
                viewModel.onBookSearchQueryChange(query)
            },
            onBookSearchSubmit = { query ->
                viewModel.submitBookSearch(query)
            },
        )
    }
}