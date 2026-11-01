package com.indigo.woorm.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.book.cover.BookCoverGrid
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.input.searchBar.SearchBar
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.common.tab.Tabs
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.library.filters.LibraryBookStatusFilter
import com.indigo.woorm.library.filters.LibraryFilters
import com.indigo.woorm.library.shelves.LibraryShelfList
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import kotlinx.coroutines.launch

const val LIBRARY_BOOKS_TAB = 0
const val LIBRARY_SHELVES_TAB = 1

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel,
    onCoverClick: (LibraryBookPreview) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToShelves: () -> Unit,
) {
    val derivedUiState by viewModel.searchState.collectAsState()
    val shelvesUiState by viewModel.shelvesState.collectAsState()
    val expandedShelves by viewModel.expandedShelves.collectAsState()

    val currentSelectedFilter = derivedUiState.filter
    var textFieldValue: TextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue()
        )
    }

    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf("Books", "Shelves")
    val pagerState = rememberPagerState(
        pageCount = {
            tabs.size
        }
    )

    Screen(
        gap = AppTheme.spacing.vertical.default,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.neutral.stroke)
                    .statusBarsPadding()
            ) {
                Tabs(
                    selectedTabIndex = pagerState.currentPage,
                    tabs = tabs,
                    onClick = { index ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    ) {
        Spacer(modifier = Modifier.height(AppTheme.spacing.vertical.componentToComponent))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top,
            pageSpacing = AppTheme.spacing.horizontal.screenMobile,

            ) { selectedTab ->
            when (selectedTab) {
                LIBRARY_BOOKS_TAB -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(space = AppTheme.spacing.vertical.sectionToSection)
                    ) {
                        SearchBar(
                            textFieldValue = textFieldValue,
                            autoFocus = false,
                            placeholderText = "Search by Title, Author or ISBN",
                            onValueChange = {
                                textFieldValue = it
                                viewModel.onQueryChange(it.text)
                            },
                            onSubmit = {
                                viewModel.submit(textFieldValue.text)
                            }
                        )

                        LibraryFilters(
                            currentSelectedFilter = currentSelectedFilter,
                            onClickFilterBy = {
                                viewModel.filter(it)
                            }
                        )
                        when (val state = derivedUiState) {
                            is LibraryUiState.Error -> {

                            }

                            is LibraryUiState.Loading -> {
                                Loading(
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            is LibraryUiState.Success -> {
                                val books = state.books
                                if (books.isEmpty()) {
                                    EmptyLibraryContent(
                                        currentSelectedFilter = currentSelectedFilter,
                                        onClickNavigateToSearch = navigateToSearch,
                                        onClickNavigateToInProgress = {
                                            viewModel.filter(LibraryBookStatusFilter.IN_PROGRESS)
                                        },
                                        onClickNavigateToTbr = {
                                            viewModel.filter(LibraryBookStatusFilter.TBR)
                                        }
                                    )
                                } else {
                                    BookCoverGrid(
                                        books = books,
                                        onCoverClick = onCoverClick,
                                        onLoadMore = { viewModel.loadNextPage() }
                                    )
                                }
                            }
                        }
                    }
                }

                LIBRARY_SHELVES_TAB -> {
                    when (val state = shelvesUiState) {
                        is LibraryShelvesUiState.Loading -> {
                            Loading(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        is LibraryShelvesUiState.Error -> {

                        }

                        is LibraryShelvesUiState.Success -> {
                            if (state.shelves.isEmpty()) {
                                Empty(
                                    modifier = Modifier.fillMaxSize(),
                                    id = Res.drawable.empty_shelves,
                                    text = "Start organizing your books in shelves"
                                ) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        variant = Variant.Primary,
                                        style = Style.Neutral,
                                        leadingIcon = AppIcon.Plus,
                                        shape = Rounded,
                                        contentDescription = "Create shelf",
                                        label = "Create shelf",
                                        onClick = navigateToShelves
                                    )
                                }
                            } else {
                                LibraryShelfList(
                                    shelves = state.shelves,
                                    expandedShelves = expandedShelves,
                                    onShelfClick = viewModel::toggleShelf,
                                    onCoverClick = onCoverClick,
                                    onLoadMore = viewModel::loadNextShelfPage,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
