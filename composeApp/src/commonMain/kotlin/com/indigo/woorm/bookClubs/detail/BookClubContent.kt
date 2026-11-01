package com.indigo.woorm.bookClubs.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.bookClubs.data.BookClubColor
import com.indigo.woorm.shareText
import com.indigo.woorm.bookClubs.detail.membersBookStatusList.MemberBookStatusList
import com.indigo.woorm.bookClubs.detail.progress.BookClubStatusProgressIndicators
import com.indigo.woorm.books.BookPreview
import com.indigo.woorm.common.book.Book
import com.indigo.woorm.common.book.cover.BookCover
import com.indigo.woorm.common.book.cover.BookCoverGrid
import com.indigo.woorm.common.bottomSheet.BottomSheet
import com.indigo.woorm.common.bottomSheet.confirm.ConfirmationBottomSheet
import com.indigo.woorm.common.bottomSheet.rememberBottomSheetState
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.chip.Chip
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.preview.ScreenPreview
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.common.snackbar.SuccessSnackbar
import com.indigo.woorm.common.tab.Tabs
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import kotlinx.coroutines.launch

const val CURRENTLY_READING_PAGE = 0
const val PREVIOUSLY_READ_PAGE = 1

@Composable
fun BookClubContent(
    bookClubDetail: BookClubDetail,
    bookSearchState: BookSearchUiState,
    isStartingBook: Boolean,
    isFinishingBook: Boolean,
    onClickBack: () -> Unit,
    onClickToBook: (Book) -> Unit,
    onClickRemoveCurrentBook: () -> Unit,
    onClickSelectCurrentBook: (Book) -> Unit,
    startCurrentBook: suspend () -> Boolean,
    finishCurrentBook: suspend () -> Boolean,
    ensureInvite: suspend () -> Invite?,
    onBookSearchStart: () -> Unit,
    onBookSearchStop: () -> Unit,
    onBookSearchQueryChange: (String) -> Unit,
    onBookSearchSubmit: (String) -> Unit,
) {
    val searchBookSheetState = rememberBottomSheetState(skipPartiallyExpanded = true)
    val finishClubBookSheetState = rememberBottomSheetState()
    val membersSheetState = rememberBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val onClickHeader = {
        membersSheetState.show()
    }

    val tabs = listOf(
        "Currently reading",
        "Previously read"
    )
    val pagerState = rememberPagerState(
        pageCount = {
            tabs.size
        }
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Screen(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                SuccessSnackbar(data)
            }
        },
        topBar = {
            Column {
                BookDetailTopBar(
                    bookClubDetail = bookClubDetail,
                    onClickHeader = onClickHeader,
                    onClickBack = onClickBack,
                    onClickShare = {
                        coroutineScope.launch {
                            val invite = bookClubDetail.invite ?: ensureInvite()
                            if (invite != null) {
                                val link = "https://woorm.com/book-clubs/invite/${invite.id}"
                                shareText(
                                    title = bookClubDetail.name,
                                    text = "Join your friends in ${bookClubDetail.name} $link"
                                )
                            } else {
                                snackbarHostState.showSnackbar(
                                    message = "No invite",
                                    withDismissAction = true
                                )
                            }
                        }
                    }
                )
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
        },
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top,
            pageSpacing = AppTheme.spacing.horizontal.screenMobile,

        ) { page ->
            when (page) {
                CURRENTLY_READING_PAGE -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Spacer(
                                modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
                            )
                        }
                        val currentBook = bookClubDetail.currentBook
                        when {
                            currentBook != null -> {
                                item {
                                    BookCover(
                                        modifier = Modifier.fillMaxWidth(0.75f),
                                        book = currentBook,
                                        onCoverClick = {
                                            onClickToBook(currentBook)
                                        }
                                    )
                                }
                                item {
                                    Spacer(
                                        modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
                                    )
                                }
                                when (bookClubDetail.currentUserStatus) {
                                    null -> {
                                        item {
                                            Button(
                                                modifier = Modifier.fillMaxWidth(),
                                                variant = Variant.Primary,
                                                style = Style.Neutral,
                                                label = "Start book",
                                                onClick = {
                                                    coroutineScope.launch {
                                                        val started = startCurrentBook()
                                                        if (started) {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Started book",
                                                                withDismissAction = true
                                                            )
                                                        }
                                                    }
                                                },
                                                shape = Rounded,
                                                leadingIcon = AppIcon.BookOpen,
                                                contentDescription = "Start book",
                                                isLoading = isStartingBook,
                                            )
                                        }
                                        item {
                                            Spacer(
                                                modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
                                            )
                                        }
                                    }
                                    LibraryBookStatusTypeDTO.IN_PROGRESS -> {
                                        item {
                                            Button(
                                                modifier = Modifier.fillMaxWidth(),
                                                variant = Variant.Primary,
                                                style = Style.Neutral,
                                                label = "Finish book",
                                                onClick = {
                                                    coroutineScope.launch {
                                                        val finished = finishCurrentBook()
                                                        if (finished) {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Finished book",
                                                                withDismissAction = true
                                                            )
                                                        }
                                                    }
                                                },
                                                shape = Rounded,
                                                leadingIcon = AppIcon.BookCheck,
                                                contentDescription = "Finish book",
                                                isLoading = isFinishingBook,
                                            )
                                        }
                                        item {
                                            Spacer(
                                                modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
                                            )
                                        }
                                    }
                                    else -> {}
                                }
                                item {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        variant = Variant.Ghost,
                                        style = Style.Neutral,
                                        label = "Finish book for book club",
                                        onClick = { finishClubBookSheetState.show() },
                                        shape = Rounded,
                                        contentDescription = "Finish book for book club",
                                    )
                                }
                                item {
                                    Spacer(
                                        modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
                                    )
                                }
                                item {

                                    BookClubStatusProgressIndicators(
                                        bookClubDetail = bookClubDetail
                                    )
                                }
                                if (
                                    bookClubDetail.memberBookStatuses.isNotEmpty() &&
                                    bookClubDetail.currentUserStatus != null &&
                                    bookClubDetail.currentUserStatus != LibraryBookStatusTypeDTO.IN_PROGRESS
                                ) {
                                    item {
                                        Spacer(
                                            modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
                                        )
                                    }
                                    item {
                                        MemberBookStatusList(
                                            bookClubDetail = bookClubDetail,
                                            memberBookStatuses = bookClubDetail.memberBookStatuses
                                        )
                                    }
                                }
                            }
                            else -> {
                                item {
                                    Empty(
                                        modifier = Modifier.fillMaxSize(),
                                        id = Res.drawable.worm_book_not_selected,
                                        text = "Choose your book clubs next great read"
                                    ) {
                                        Button(
                                            modifier = Modifier.fillMaxWidth(),
                                            variant = Variant.Primary,
                                            style = Style.Neutral,
                                            label = "Search for book",
                                            onClick = {
                                                searchBookSheetState.show()
                                            },
                                            shape = Rounded,
                                            contentDescription = "Search for book",
                                        )
                                    }
                                }
                                item {
                                    Spacer(
                                        modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(Modifier.height(100.dp))
                        }
                    }
                }

                PREVIOUSLY_READ_PAGE -> {
                    BookCoverGrid(
                        books = bookClubDetail.books,
                        onCoverClick = onClickToBook,
                        onLoadMore = {},
                        contentPadding = PaddingValues(
                            top = AppTheme.spacing.vertical.sectionToSection
                        ),
                    )
                }
            }
        }
    }
    BottomSheet(
        state = searchBookSheetState,
        header = {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    variant = Variant.Ghost,
                    style = Style.Neutral,
                    onClick = { searchBookSheetState.hide() },
                    icon = AppIcon.X,
                    contentDescription = "Back"
                )
                Text(
                    text = "Search your library",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppTheme.colors.neutral.text
                )
            }
        }
    ) {
        BookClubBookPicker(
            state = bookSearchState,
            onSearchStart = onBookSearchStart,
            onSearchStop = onBookSearchStop,
            onQueryChange = onBookSearchQueryChange,
            onSubmit = onBookSearchSubmit,
            onSelectBook = { book ->
                onClickSelectCurrentBook(book)
                searchBookSheetState.hide()
            },
        )
    }

    BottomSheet(
        state = membersSheetState,
        header = {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    variant = Variant.Ghost,
                    style = Style.Neutral,
                    onClick = { membersSheetState.hide() },
                    icon = AppIcon.X,
                    contentDescription = "Back"
                )
                Text(
                    text = "Members",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppTheme.colors.neutral.text
                )
            }
        }
    ) {
        bookClubDetail.members.forEach { member ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = member.username,
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppTheme.colors.neutral.text
                )
                if (member.isAdmin) {
                    Chip(label = "Admin")
                }
            }
            Spacer(
                modifier = Modifier.height(AppTheme.spacing.vertical.componentToComponent)
            )
        }
    }

    ConfirmationBottomSheet(
        state = finishClubBookSheetState,
        onConfirm = {
            onClickRemoveCurrentBook()
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Finished book",
                    withDismissAction = true
                )
            }
        },
        header = {
            Text(
                text = "Finish book for book club",
                style = MaterialTheme.typography.headlineSmall,
                color = AppTheme.colors.neutral.text
            )
        }
    ) {
        Text(
            text = "\"${bookClubDetail.currentBook?.title}\" will be finished for all members and moved to previously read.",
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text
        )
    }
}

@Preview
@Composable
private fun BookClubContentPreview() {
    ScreenPreview {
        BookClubContent(
            bookClubDetail = BookClubDetail(
                id = "1",
                name = "Totally not a book club",
                color = BookClubColor.BLUE,
                books = listOf(),
                currentBook = BookPreview(
                    id = 92,
                    coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1388462753i/52318.jpg",
                    title = "Wild Seed (Patternist, #1)",
                    authors = emptyList()
                ),
                readingProgress = mapOf(
                    LibraryBookStatusTypeDTO.IN_PROGRESS to 2,
                    LibraryBookStatusTypeDTO.READ to 1,
                    LibraryBookStatusTypeDTO.DNF to 0
                ),
                invite = null,
                members = listOf(
                    Member(
                        userId = "8acdfb63-0dbf-4b6f-89ef-0dbd69ed19e4",
                        username = "bookworm92",
                        isAdmin = true
                    ),
                    Member(
                        userId = "3f9d8a3e-6a20-4621-a70e-3a71f0bbcd51",
                        username = "pages_and_pens",
                        isAdmin = false
                    ),
                    Member(
                        userId = "b73c6e21-4c74-4c3e-97e5-5d8de49d5a17",
                        username = "midnight_reader",
                        isAdmin = false
                    ),
                ),
            ),
            bookSearchState = BookSearchUiState.Idle,
            isStartingBook = false,
            isFinishingBook = false,
            onClickBack = {},
            onClickToBook = {},
            onClickRemoveCurrentBook = { },
            onClickSelectCurrentBook = { },
            startCurrentBook = { false },
            finishCurrentBook = { false },
            ensureInvite = { null },
            onBookSearchStart = {},
            onBookSearchStop = {},
            onBookSearchQueryChange = {},
            onBookSearchSubmit = {},
        )
    }
}
