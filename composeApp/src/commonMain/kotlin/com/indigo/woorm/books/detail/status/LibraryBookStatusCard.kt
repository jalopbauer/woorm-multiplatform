package com.indigo.woorm.books.detail.status

import androidx.compose.runtime.Composable
import com.indigo.woorm.books.detail.viewModel.LibraryBookUiState
import com.indigo.woorm.books.detail.viewModel.action.BookDnf
import com.indigo.woorm.books.detail.viewModel.action.BookInProgress
import com.indigo.woorm.books.detail.viewModel.action.BookRead
import com.indigo.woorm.books.detail.viewModel.action.ReadAgain
import com.indigo.woorm.common.card.Card
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.library.books.status.LibraryBookStatus

@Composable
fun LibraryBookStatusCard(
    status: LibraryBookStatus?,
    onClickInProgress: () -> Unit,
    onClickRead: () -> Unit,
    onClickDnf: () -> Unit,
    onClickReadAgain: () -> Unit,
    isLibraryBookEditing: LibraryBookUiState.Editing?
) {
    val isLoadingStatusChange = isLibraryBookEditing?.bookDetailAction.let {
        it is BookInProgress || it is BookRead || it is BookDnf
    }
    Card {
        LibraryBookStatusSelect(
            status = status,
            label = "In progress",
            icon = AppIcon.BookOpen,
            isLoading = isLibraryBookEditing?.bookDetailAction is BookInProgress,
            selectedStatus = {
                !isLoadingStatusChange && it is LibraryBookStatus.InProgress
            },
            onClick = onClickInProgress,
        )
        LibraryBookStatusSelect(
            status = status,
            icon = AppIcon.BookCheck,
            label = "Read",
            isLoading = isLibraryBookEditing?.bookDetailAction is BookRead,
            selectedStatus = {
                !isLoadingStatusChange && it is LibraryBookStatus.Read
            },
            onClick = onClickRead,
        )
        LibraryBookStatusSelect(
            status = status,
            icon = AppIcon.BookX,
            label = "Did not finish (DNF)",
            selectedStatus = {
                !isLoadingStatusChange && it is LibraryBookStatus.Dnf
            },
            onClick = onClickDnf,
            isLoading = isLibraryBookEditing?.bookDetailAction is BookDnf,
        )
        ReadBookAgain(
            status = status,
            onClickReadAgain = onClickReadAgain,
            isLoading = isLibraryBookEditing?.bookDetailAction is ReadAgain
        )
    }
}