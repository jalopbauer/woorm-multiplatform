package com.indigo.woorm.books.search

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChangeIgnoreConsumed
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.books.BookPreview
import com.indigo.woorm.common.book.cover.BookCoverGrid
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.common.input.searchBar.SearchBar
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import kotlin.math.abs


@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onCoverClick: (BookPreview) -> Unit
) {
    val searchUiState by viewModel.searchState.collectAsState()
    val focusManager = LocalFocusManager.current
    var textFieldValue: TextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Screen (
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
            .pointerInput(Unit) {
                awaitEachGesture {
                    var drag = Offset.Zero
                    var tracked: PointerInputChange? = null
                    while (true) {
                        val event = awaitPointerEvent(pass = PointerEventPass.Initial)
                        val change = tracked?.let { trackedChange ->
                            event.changes.firstOrNull { it.id == trackedChange.id }
                        } ?: event.changes.firstOrNull { it.pressed }?.also { tracked = it }
                            ?: return@awaitEachGesture
                        drag += change.positionChangeIgnoreConsumed()
                        if (abs(drag.x) > viewConfiguration.touchSlop || abs(drag.y) > viewConfiguration.touchSlop) {
                            focusManager.clearFocus()
                            return@awaitEachGesture
                        }
                        if (event.changes.none { it.pressed }) return@awaitEachGesture
                    }
                }
            },
        gap = AppTheme.spacing.vertical.default
    ) {
        SearchBar(
            textFieldValue = textFieldValue,
            placeholderText = "Search by Title, Author or ISBN",
            onValueChange = {
                textFieldValue = it
                viewModel.onQueryChange(it.text)
            },
            onSubmit = {
                viewModel.submit(textFieldValue.text)
            }
        )
        when (searchUiState.status) {
            SearchUiState.Status.Idle -> {
                Empty(
                    modifier = Modifier.fillMaxSize(),
                    id = Res.drawable.worm_sherlock,
                    text = "Start searching for your next great read",
                    callToAction = null
                )
            }

            SearchUiState.Status.Loading -> {
                Loading(
                    modifier = Modifier.fillMaxSize()
                )
            }

            SearchUiState.Status.Empty -> {
                Empty(
                    modifier = Modifier.fillMaxSize(),
                    id = Res.drawable.worm_sherlock,
                    text = "No results for \"${searchUiState.query}\" yet"
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        variant = Variant.Primary,
                        style = Style.Neutral,
                        shape = Rounded,
                        label = "Clear search",
                        leadingIcon = AppIcon.X,
                        onClick = {
                            textFieldValue = TextFieldValue()
                        },
                        contentDescription = "Clear search"
                    )
                }
            }

            SearchUiState.Status.Error -> {
                Empty(
                    modifier = Modifier.fillMaxSize(),
                    id = Res.drawable.worm_sherlock,
                    text = searchUiState.errorMessage ?: "We couldn't complete the search"
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        variant = Variant.Primary,
                        style = Style.Neutral,
                        shape = Rounded,
                        label = "Clear search",
                        leadingIcon = AppIcon.X,
                        onClick = {
                            textFieldValue = TextFieldValue()
                        },
                        contentDescription = "Clear search"
                    )
                }
            }

            SearchUiState.Status.Success -> {
                BookCoverGrid(
                    books = searchUiState.books,
                    isAppending = searchUiState.isAppending,
                    onCoverClick = onCoverClick,
                    onLoadMore = {
                        if (searchUiState.canLoadMore && !searchUiState.isAppending) {
                            viewModel.loadNextPage()
                        }
                    }
                )
            }
        }
    }
}
