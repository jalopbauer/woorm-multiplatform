package com.indigo.woorm.books.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.indigo.woorm.books.detail.data.BookDetailData
import com.indigo.woorm.books.detail.data.BookDetailTopAppBar
import com.indigo.woorm.books.detail.status.LibraryBookStatus
import com.indigo.woorm.books.detail.status.list.LibraryBookStatusList
import com.indigo.woorm.books.detail.viewModel.LibraryBookUiState
import com.indigo.woorm.books.detail.viewModel.action.BookDetailAction
import com.indigo.woorm.books.detail.viewModel.action.CreateShelf
import com.indigo.woorm.books.detail.viewModel.action.ToggleShelf
import com.indigo.woorm.common.book.cover.BookCover
import com.indigo.woorm.common.bottomSheet.BottomSheet
import com.indigo.woorm.common.bottomSheet.rememberBottomSheetState
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.select.multiple.MultiSelectItem
import com.indigo.woorm.common.snackbar.SuccessSnackbar
import com.indigo.woorm.library.books.LibraryBook
import com.indigo.woorm.library.books.shelves.LibraryShelf
import com.indigo.woorm.library.books.shelves.ShelvesChipFlow
import com.indigo.woorm.library.books.shelves.ShelvesSelectionFlow
import com.indigo.woorm.library.books.status.LibraryBookStatus
import com.indigo.woorm.shelves.CreateShelfBottomSheet
import com.indigo.woorm.ui.theme.AppTheme
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookDetailContent(
    book: BookDetail,
    libraryBook: LibraryBook?,
    isLibraryBookEditing: LibraryBookUiState.Editing?,
    shelves: List<LibraryShelf>,
    onClickBackIconButton: () -> Unit,
    onClickBookInLibrary: () -> Unit,
    onClickAct: (BookDetailAction) -> Unit,
    onClickStartDate: (LocalDate?) -> Unit,
    onClickEndDate: (LocalDate?) -> Unit,
    rate: (Double) -> Unit,
    onSaveComment: (String) -> Unit,
    onClickReadAgain: () -> Unit,
    onClickDeleteStatus: (LibraryBookStatus) -> Unit,
    onClickCreateShelf: (String) -> Unit,
    toggleShelf: (LibraryShelf) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    val filterSheetState = rememberBottomSheetState()
    val historySheetState = rememberBottomSheetState()
    val createShelfSheetState = rememberBottomSheetState()
    var newShelfName by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                SuccessSnackbar(data)
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                state = listState,
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.neutral.background),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                item {
                    BookCover(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        book = book
                    )
                }
                item {
                    Text(
                        text = book.title,
                        color = AppTheme.colors.neutral.text,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                    )
                }
                item {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = AppTheme.spacing.horizontal.screenMobile),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.sectionToSection)
                    ) {
                        Column {
                            BookDetailData(
                                book = book
                            )
                            LibraryBookStatus(
                                libraryBook = libraryBook,
                                isLibraryBookEditing = isLibraryBookEditing,
                                onClickBookInLibrary = onClickBookInLibrary,
                                onClickStartDate = onClickStartDate,
                                onClickEndDate = onClickEndDate,
                                onClickReadAgain = onClickReadAgain,
                                rate = rate,
                                onSaveComment = onSaveComment,
                                onClickAct = onClickAct,
                                onClickShowHistory = {
                                    historySheetState.show()
                                }
                            )
                        }

                        libraryBook?.shelves ?.let {
                            ShelvesChipFlow(
                                shelves = libraryBook.shelves,
                                onClick = { filterSheetState.show() }
                            )
                        }
                    }

                }
                item {
                    Spacer(Modifier.height(100.dp))
                }
            }

            BottomSheet(
                header = {
                    Text(
                        text = "Shelves",
                        color = AppTheme.colors.neutral.text,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                state = filterSheetState
            ) {
                ShelvesSelectionFlow(
                    shelves = shelves,
                    onClick = {
                        createShelfSheetState.show()
                    },
                    isLoading = isLibraryBookEditing?.bookDetailAction is CreateShelf
                            || isLibraryBookEditing?.bookDetailAction is ToggleShelf,
                ) { libraryShelf ->
                    MultiSelectItem(
                        selected = libraryShelf.selected,
                        label = libraryShelf.name,
                        leadingIcon = if (libraryShelf.selected) AppIcon.X else AppIcon.Plus,
                        onClick = {
                            toggleShelf(libraryShelf)
                        },
                    )
                }

            }

            BottomSheet(
                header = {
                    Text(
                        text = "Reading history",
                        color = AppTheme.colors.neutral.text,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                state = historySheetState
            ) {
                LibraryBookStatusList(
                    libraryBook = libraryBook,
                    onClickDeleteStatus = {
                        historySheetState.hide()
                        onClickDeleteStatus(it)
                    }
                )
            }

            CreateShelfBottomSheet(
                createShelfSheetState = createShelfSheetState,
                newShelfName = newShelfName,
                onConfirm = {
                    onClickCreateShelf(newShelfName.trim())
                    newShelfName = ""
                },
                onValueChange = {
                    newShelfName = it
                }
            )
        }

        BookDetailTopAppBar(
            book = book,
            isScrolled = isScrolled,
            onClickBackIconButton = onClickBackIconButton
        )
    }
}