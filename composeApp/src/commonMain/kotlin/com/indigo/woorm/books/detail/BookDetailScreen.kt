package com.indigo.woorm.books.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.indigo.woorm.books.detail.viewModel.BookUiState
import com.indigo.woorm.books.detail.viewModel.BookDetailViewModel
import com.indigo.woorm.books.detail.viewModel.LibraryBookUiState
import com.indigo.woorm.books.detail.viewModel.action.BookRead
import com.indigo.woorm.books.detail.viewModel.action.CreateShelf
import com.indigo.woorm.books.detail.viewModel.action.DeleteStatusHistory
import com.indigo.woorm.books.detail.viewModel.action.Rate
import com.indigo.woorm.books.detail.viewModel.action.ReadAgain
import com.indigo.woorm.books.detail.viewModel.action.SaveComment
import com.indigo.woorm.books.detail.viewModel.action.SetEndDate
import com.indigo.woorm.books.detail.viewModel.action.SetStartDate
import com.indigo.woorm.books.detail.viewModel.action.ToLibrary
import com.indigo.woorm.books.detail.viewModel.action.ToggleShelf
import com.indigo.woorm.books.detail.status.ReadPromptBottomSheet
import com.indigo.woorm.common.bottomSheet.confirm.ConfirmationBottomSheet
import com.indigo.woorm.common.bottomSheet.rememberBottomSheetState
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.library.books.status.LibraryBookStatus
import com.indigo.woorm.ui.theme.AppTheme
import kotlin.time.Instant
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel,
    onClickBackIconButton: () -> Unit
) {
    val derivedUiState by viewModel.bookUiState.collectAsState()
    val libraryBookUiState by viewModel.libraryBookUiState.collectAsState()
    
    val datePickerBottomSheetState = rememberBottomSheetState(
        skipPartiallyExpanded = true
    )
    val readPromptBottomSheetState = rememberBottomSheetState()
    val datePickerState = rememberDatePickerState()
    var isPickingStartDate by remember { mutableStateOf(true) }

    val success by viewModel.success.collectAsState()
    val error by viewModel.error.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(success) {
        success?.let {
            snackbarHostState.showSnackbar(
                message = it.description,
                withDismissAction = true
            )
        }
    }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(
                message = it,
                withDismissAction = true
            )
        }
    }

    when (val uiState = derivedUiState) {
        is BookUiState.Error -> {
            Scaffold { innerPadding ->
                Text(modifier = Modifier.padding(innerPadding), text = "Error: ${uiState.message}")
            }
        }

        BookUiState.Loading -> {
            Loading(
                modifier = Modifier.fillMaxSize()
            )
        }

        is BookUiState.Success -> {
            val book = uiState.book
            val libraryBook = when (val libState = libraryBookUiState) {
                is LibraryBookUiState.Success -> libState.libraryBook
                is LibraryBookUiState.Editing -> libState.libraryBook
                else -> null
            }
            val shelves = when (val libState = libraryBookUiState) {
                is LibraryBookUiState.Success -> libState.shelves
                else -> emptyList()
            }
            BookDetailContent(
                snackbarHostState = snackbarHostState,
                book = book,
                libraryBook = libraryBook,
                isLibraryBookEditing = when (val libState = libraryBookUiState) {
                    is LibraryBookUiState.Editing -> libState
                    else -> null
                },
                shelves = shelves,
                onClickBackIconButton = onClickBackIconButton,
                onClickBookInLibrary = {
                    viewModel.act(ToLibrary())
                },
                onClickAct = { action ->
                    val libState = libraryBookUiState
                    val isReadWithNoCurrentStatus = action is BookRead
                            && libState is LibraryBookUiState.Success
                            && libState.libraryBook?.currentStatus == null
                    if (isReadWithNoCurrentStatus) {
                        readPromptBottomSheetState.show()
                    } else {
                        viewModel.act(action)
                    }
                },
                onClickReadAgain = {
                    viewModel.act(ReadAgain())
                },
                onClickStartDate = {
                    isPickingStartDate = true
                    datePickerState.selectedDateMillis = libraryBook?.currentStatus?.startDate
                        ?.atStartOfDayIn(TimeZone.UTC)
                        ?.toEpochMilliseconds()
                    datePickerBottomSheetState.show()
                },
                onClickEndDate = {
                    isPickingStartDate = false
                    val endDate = (libraryBook?.currentStatus as? LibraryBookStatus.Read)?.endDate
                    datePickerState.selectedDateMillis = endDate
                        ?.atStartOfDayIn(TimeZone.UTC)
                        ?.toEpochMilliseconds()
                    datePickerBottomSheetState.show()
                },
                onClickDeleteStatus = { status ->
                    viewModel.act(DeleteStatusHistory(status))
                },
                toggleShelf = {
                    viewModel.act(ToggleShelf(it))
                },
                onClickCreateShelf = {
                    viewModel.act(CreateShelf(it))
                },
                rate = {
                    viewModel.act(Rate(it))
                },
                onSaveComment = {
                    viewModel.act(SaveComment(it))
                }
            )
        }
    }

    ConfirmationBottomSheet(
        state = datePickerBottomSheetState,
        header = {
            Text(
                text = if (isPickingStartDate) "Select start date" else "Select end date",
                style = MaterialTheme.typography.headlineSmall,
                // The sheet container is AppTheme neutral.background (light beige in both
                // themes), so a Material colorScheme color (e.g. onSurface in dark mode)
                // can blend into it. Use the domain text color instead.
                color = AppTheme.colors.neutral.text
            )
        },
        onConfirm = {
            val selectedDate = datePickerState.selectedDateMillis?.let {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.UTC).date
            }
            if (isPickingStartDate) {
                viewModel.act(SetStartDate(selectedDate))
            } else {
                viewModel.act(SetEndDate(selectedDate))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = AppTheme.colors.neutral.background,
                // Text content colors: neutral.text (Brown800) contrasts with the
                // neutral.background (Brown100) container.
                titleContentColor = AppTheme.colors.neutral.text,
                headlineContentColor = AppTheme.colors.neutral.text,
                weekdayContentColor = AppTheme.colors.neutral.text,
                subheadContentColor = AppTheme.colors.neutral.text,
                navigationContentColor = AppTheme.colors.neutral.text,
                dayContentColor = AppTheme.colors.neutral.text,
                yearContentColor = AppTheme.colors.neutral.text,
                // "Today" emphasis: neutral.highlight (Brown700) stays readable on Brown100
                // while distinguishing the current date/year from the rest.
                currentYearContentColor = AppTheme.colors.neutral.highlight,
                todayContentColor = AppTheme.colors.neutral.highlight,
                todayDateBorderColor = AppTheme.colors.neutral.primary,
                dividerColor = AppTheme.colors.neutral.stroke,
                // Selected cells use neutral.primary (Brown500) as their container, so their
                // content needs to be light: neutral.background (Brown100) reads well on it.
                selectedDayContainerColor = AppTheme.colors.neutral.primary,
                selectedDayContentColor = AppTheme.colors.neutral.background,
                selectedYearContainerColor = AppTheme.colors.neutral.primary,
                selectedYearContentColor = AppTheme.colors.neutral.background
            ),
            title = null,
            showModeToggle = false,
            modifier = Modifier.fillMaxWidth()
        )
    }

    ReadPromptBottomSheet(
        state = readPromptBottomSheetState,
        title = "Did you finish reading today?",
        text = "Books read this year count toward your reading goal.",
        closeContentDescription = "Close",
        noLabel = "No",
        yesLabel = "Yes",
        onNo = {
            viewModel.act(BookRead(finishedToday = false))
        },
        onYes = {
            viewModel.act(BookRead(finishedToday = true))
        },
    )
}
