package com.indigo.woorm.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import androidx.lifecycle.compose.LifecycleResumeEffect
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import com.indigo.woorm.common.bottomSheet.BottomSheet
import com.indigo.woorm.common.bottomSheet.rememberBottomSheetState
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.common.snackbar.SuccessSnackbar
import com.indigo.woorm.home.bookClubs.BookClubUpdates
import com.indigo.woorm.goals.composables.GoalInput
import com.indigo.woorm.home.goals.HomeTopBar
import com.indigo.woorm.home.inProgress.BookInProgressCardPager
import com.indigo.woorm.home.viewModel.GoalUiState
import com.indigo.woorm.home.viewModel.HomeViewModel
import com.indigo.woorm.home.viewModel.InProgressLibraryUiState
import com.indigo.woorm.home.viewModel.action.BookToDnf
import com.indigo.woorm.home.viewModel.action.BookToRead
import com.indigo.woorm.home.viewModel.action.SetGoal
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickBookDetailCard : (LibraryBookPreview) -> Unit,
    onClickBookClubCard : (BookClubPreview) -> Unit
) {
    val inProgressLibraryUiState by viewModel.inProgressState.collectAsState()
    val currentGoalStateDerived by viewModel.currentGoalState.collectAsState()
    val bookClubPreviews by viewModel.bookClubPreviews.collectAsState()
    val success by viewModel.success.collectAsState()
    val error by viewModel.error.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    LifecycleResumeEffect(Unit) {
        viewModel.retry()
        onPauseOrDispose {
        }
    }
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

    val confirmationBottomSheetState = rememberBottomSheetState(
        skipPartiallyExpanded = true
    )

    val goal = remember(currentGoalStateDerived) {
        when (val state = currentGoalStateDerived) {
            is GoalUiState.Success -> state.goal
            else -> null
        }
    }

    when (val state = inProgressLibraryUiState) {
        InProgressLibraryUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Empty(
                    id = Res.drawable.worm_book_not_selected,
                    text = "We couldn't reach the server. It may be waking up.",
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.spacing.horizontal.screenMobile),
                        variant = Variant.Primary,
                        style = Style.Neutral,
                        shape = Rounded,
                        label = "Retry",
                        contentDescription = "Retry loading home",
                        onClick = { viewModel.retry() }
                    )
                }
            }
        }

        InProgressLibraryUiState.Loading -> {
            Loading(
                modifier = Modifier.fillMaxSize()
            )
        }

        is InProgressLibraryUiState.Success -> {
            Screen(
                snackbarHost = {
                    SnackbarHost(snackbarHostState) { data ->
                        SuccessSnackbar(data)
                    }
                },
                topBar = {
                    HomeTopBar(
                        goal = goal,
                        onClick = {
                            confirmationBottomSheetState.show()
                        }
                    )
                },
                gap = AppTheme.spacing.vertical.default,
                horizontalPadding = AppTheme.spacing.horizontal.none

            ) {
                Spacer(Modifier.height(AppTheme.spacing.vertical.textToText))
                BookClubUpdates(
                    modifier = Modifier.weight(1f).padding(horizontal = AppTheme.spacing.horizontal.screenMobile),
                    bookClubPreviews = bookClubPreviews,
                    onClickBookClubCard = onClickBookClubCard
                )
                if (state.books.isEmpty()) {
                    Image(
                        painter = painterResource(Res.drawable.empty_in_progress),
                        contentDescription = "Start your next great read and see it here"
                    )
                } else {
                    BookInProgressCardPager(
                        libraryBooks = state.books,
                        onClickBookDetailCard = onClickBookDetailCard,
                        onDnfClick = {
                            viewModel.act(
                                BookToDnf(
                                    it
                                )
                            )
                        },
                        onReadClick = {
                            viewModel.act(
                                BookToRead(
                                    it
                                )
                            )
                        },
                    )
                }
                Spacer(Modifier.height(AppTheme.spacing.vertical.textToText))
            }

            BottomSheet(
                state = confirmationBottomSheetState,
            ) {
                GoalInput(
                    goal = goal,
                    onConfirm = { amount ->
                        viewModel.act(
                            SetGoal(
                                amount = amount
                            )
                        )
                        confirmationBottomSheetState.hide()
                    },
                    onCancel = {
                        confirmationBottomSheetState.hide()
                    }
                )
            }
        }
    }
}