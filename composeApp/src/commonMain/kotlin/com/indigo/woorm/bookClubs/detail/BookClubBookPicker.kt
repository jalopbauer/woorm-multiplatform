package com.indigo.woorm.bookClubs.detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.book.Book
import com.indigo.woorm.common.book.cover.BookCoverGrid
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.input.searchBar.SearchBar
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookClubBookPicker(
    state: BookSearchUiState,
    onSearchStart: () -> Unit,
    onSearchStop: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
    onSelectBook: (Book) -> Unit,
) {
    var textFieldValue: TextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(Unit) {
        onSearchStart()
    }

    DisposableEffect(Unit) {
        onDispose {
            onSearchStop()
        }
    }

    SearchBar(
        textFieldValue = textFieldValue,
        placeholderText = "Search by Title, Author or ISBN",
        onValueChange = {
            textFieldValue = it
            onQueryChange(it.text)
        },
        onSubmit = {
            onSubmit(textFieldValue.text)
        }
    )
    Spacer(
        modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
    )
    when (state) {
        BookSearchUiState.Idle -> {
            Empty(
                modifier = Modifier.fillMaxSize(),
                id = Res.drawable.worm_sherlock,
                text = "Start searching for your next great read",
                callToAction = null
            )
        }

        is BookSearchUiState.Loading -> {
            Loading(
                modifier = Modifier.fillMaxSize()
            )
        }

        is BookSearchUiState.Empty -> {
            Empty(
                modifier = Modifier.fillMaxSize(),
                id = Res.drawable.worm_sherlock,
                text = "No results for \"${state.query}\" yet",
                callToAction = null
            )
        }

        is BookSearchUiState.Error -> {
            Empty(
                modifier = Modifier.fillMaxSize(),
                id = Res.drawable.worm_sherlock,
                text = state.errorMessage ?: "We couldn't complete the search",
                callToAction = null
            )
        }

        is BookSearchUiState.Success -> {
            BookCoverGrid(
                books = state.books,
                onCoverClick = onSelectBook,
                onLoadMore = {}
            )
        }
    }
}
