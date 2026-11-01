package com.indigo.woorm.books.detail.status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.books.detail.status.comment.FinalComment
import com.indigo.woorm.books.detail.viewModel.LibraryBookUiState
import com.indigo.woorm.books.detail.viewModel.action.BookDetailAction
import com.indigo.woorm.books.detail.viewModel.action.BookDnf
import com.indigo.woorm.books.detail.viewModel.action.BookInProgress
import com.indigo.woorm.books.detail.viewModel.action.BookRead
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.card.Card
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.books.detail.status.stars.Stars
import com.indigo.woorm.library.books.LibraryBook
import com.indigo.woorm.library.books.status.LibraryBookStatus
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Square
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import kotlinx.datetime.LocalDate

@Composable
fun LibraryBookStatus(
    libraryBook: LibraryBook?,
    onClickBookInLibrary: () -> Unit,
    onClickAct: (BookDetailAction) -> Unit,
    onClickReadAgain: () -> Unit,
    onClickShowHistory: () -> Unit,
    onClickStartDate: (LocalDate?) -> Unit,
    onClickEndDate: (LocalDate?) -> Unit,
    rate: (Double) -> Unit,
    onSaveComment: (String) -> Unit,
    isLibraryBookEditing: LibraryBookUiState.Editing?,
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default)
    ){
        BookInLibrary(
            libraryBook = libraryBook,
            onClickBookInLibrary = onClickBookInLibrary,
            isLibraryBookEditing = isLibraryBookEditing,
        )
        val status = libraryBook?.currentStatus
        LibraryBookStatusCard(
            status = status,
            onClickInProgress = {
                onClickAct(BookInProgress())
            },
            onClickRead = {
                onClickAct(BookRead(finishedToday = true))
            },
            onClickDnf = {
                onClickAct(BookDnf())
            },
            onClickReadAgain = onClickReadAgain,
            isLibraryBookEditing = isLibraryBookEditing
        )
        if (status != null) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                variant = Variant.Surface,
                style = Style.Neutral,
                shape = Rounded,
                label = "Start date" +  ": " + (status.startDate?.toString() ?: ""),
                contentDescription = "Start date",
                leadingIcon = AppIcon.CalendarPlus2,
                onClick = {
                    onClickStartDate(status.startDate)
                }
            )
        }
        if (status is LibraryBookStatus.Read) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                variant = Variant.Surface,
                style = Style.Neutral,
                shape = Rounded,
                label = "End date" + ":  " + (status.endDate?.toString() ?: ""),
                contentDescription = "End date",
                leadingIcon = AppIcon.CalendarCheck,
                onClick = {
                    onClickEndDate(status.endDate)
                }
            )
        }
        if (!libraryBook?.statuses.isNullOrEmpty()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                variant = Variant.Ghost,
                style = Style.Neutral,
                label = "Reading history",
                shape = Rounded,
                onClick = onClickShowHistory,
                contentDescription = "Show reading history",
                leadingIcon = AppIcon.RotateCcw
            )
        }
        if (status != null && libraryBook.currentStatus is LibraryBookStatus.Read) {
            Stars(
                rating = libraryBook.currentStatus.userRating ?: 0.0,
                rate = rate,
                isLibraryBookEditing = isLibraryBookEditing
            )
        }
        if (libraryBook != null && libraryBook.statuses.any {
                it is LibraryBookStatus.Read || it is LibraryBookStatus.Dnf
            }) {
            FinalComment(
                comment = libraryBook.finalComment,
                onSaveComment = onSaveComment
            )
        }
    }
}