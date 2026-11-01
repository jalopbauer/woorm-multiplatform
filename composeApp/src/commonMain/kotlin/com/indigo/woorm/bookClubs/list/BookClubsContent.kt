package com.indigo.woorm.bookClubs.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.bookClubs.data.BookClubColor
import com.indigo.woorm.bookClubs.data.BookClubData
import com.indigo.woorm.bookClubs.data.BookClubDetailsForm
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import com.indigo.woorm.bookClubs.previews.BookClubPreviews
import com.indigo.woorm.common.bottomSheet.confirm.ConfirmationBottomSheet
import com.indigo.woorm.common.bottomSheet.rememberBottomSheetState
import com.indigo.woorm.common.button.fab.FloatingActionButton
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.preview.ScreenPreview
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.ui.theme.AppTheme


@Composable
fun BookClubsContent(
    bookClubPreviews: List<BookClubPreview>,
    onClickCreateBookClub: (BookClubData) -> Unit,
    onBookClubClick: (BookClubPreview) -> Unit
) {
    val createBookClubBottomSheet = rememberBottomSheetState()

    Screen(
        floatingActionButton = {
            FloatingActionButton(
                icon = AppIcon.Plus,
                contentDescription = "Create book club",
                onFabClick = {
                    createBookClubBottomSheet.show()
                },
            )
        }
    ) {
        if (bookClubPreviews.isEmpty()) {
            Empty(
                modifier = Modifier.fillMaxSize(),
                id = Res.drawable.worm_book_club_invite,
                text = "Create or join your friends in reading",
                callToAction = null
            )
        } else {
            BookClubPreviews(
                bookClubPreviews = bookClubPreviews,
                onClick = onBookClubClick
            )
        }
    }

    val initial = BookClubData(
        name = "",
        color = BookClubColor.BLUE
    )
    var bookClubData by remember { mutableStateOf(initial) }
    ConfirmationBottomSheet(
        createBookClubBottomSheet,
        onConfirm = {
            onClickCreateBookClub(bookClubData.copy(name = bookClubData.name.trim()))
            bookClubData = initial
        },
        confirmEnabled = bookClubData.name.trim().isNotBlank(),
        header = {
            Text(
                text = "Create book club",
                style = MaterialTheme.typography.headlineSmall,
                color = AppTheme.colors.neutral.text
            )
        }
    ) {
        BookClubDetailsForm(
            bookClubData = bookClubData,
            onNameChange = {
                bookClubData = BookClubData(
                    name = it,
                    color = bookClubData.color
                )
            },
        ) {
            bookClubData = BookClubData(
                name = bookClubData.name,
                color = it
            )
        }
    }
}

@Preview
@Composable
private fun BookClubList_TwoBookClubs_Preview() {
    ScreenPreview {
        BookClubsContent(
            bookClubPreviews = listOf(
                BookClubPreview(
                    id = "1",
                    name = "Totally not a book club",
                    color = BookClubColor.BLUE,
                    currentBook = null,
                    readingProgress = emptyMap()
                ),
                BookClubPreview(
                    id = "2",
                    name = "Les Bacci",
                    color = BookClubColor.GREEN,
                    currentBook = null,
                    readingProgress = emptyMap()
                )
            ),
            onClickCreateBookClub = {},
            onBookClubClick = {}
        )
    }
}


@Preview
@Composable
private fun BookClubList_TenBookClubs_Preview() {
    ScreenPreview {
        BookClubsContent(
            bookClubPreviews = (0..10).map {
                BookClubPreview(
                    id = it.toString(),
                    name = "Club $it",
                    color = BookClubColor.BLUE,
                    currentBook = null,
                    readingProgress = emptyMap()
                )
            },
            onClickCreateBookClub = {},
            onBookClubClick = {}
        )
    }
}