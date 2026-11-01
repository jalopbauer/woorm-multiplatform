package com.indigo.woorm.home.viewModel

import androidx.lifecycle.viewModelScope
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import com.indigo.woorm.bookClubs.service.BookClubService
import com.indigo.woorm.goals.GoalPreview
import com.indigo.woorm.goals.service.GoalService
import com.indigo.woorm.home.viewModel.action.BookToDnf
import com.indigo.woorm.home.viewModel.action.BookToRead
import com.indigo.woorm.home.viewModel.action.HomeAction
import com.indigo.woorm.home.viewModel.action.SetGoal
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.library.service.LibraryBookService
import com.indigo.woorm.library.service.dto.EditLibraryBookStatusDto
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.library.service.dto.toLibraryBookPreview
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import com.indigo.woorm.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.todayIn
import kotlinx.datetime.TimeZone

class HomeViewModel(
    private val libraryBookService: LibraryBookService,
    private val goalService: GoalService,
    private val bookClubService: BookClubService,
) : BaseViewModel<HomeAction>() {
    private val _inProgressState = MutableStateFlow<InProgressLibraryUiState>(InProgressLibraryUiState.Loading)
    val inProgressState: StateFlow<InProgressLibraryUiState> = _inProgressState.asStateFlow()

    private val _currentGoalState = MutableStateFlow<GoalUiState>(GoalUiState.Loading)
    val currentGoalState: StateFlow<GoalUiState> = _currentGoalState.asStateFlow()

    private var _bookClubPreviews = MutableStateFlow<List<BookClubPreview>>(emptyList())
    val bookClubPreviews = _bookClubPreviews.asStateFlow()


    init {
        load()
    }

    private fun allBookClubs() {
        viewModelScope.launch {
            bookClubService.all().onSuccess {
                _bookClubPreviews.emit(it.filter(BookClubPreview::isCurrentBookSet))
            }
        }
    }

    fun retry() {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _inProgressState.emit(InProgressLibraryUiState.Loading)
        }
        currentGoal()
        inProgress()
        allBookClubs()
    }

    private fun currentGoal() {
        viewModelScope.launch {
            try {
                val currentGoal = goalService.currentGoal()
                _currentGoalState.update {
                    GoalUiState.Success(currentGoal)
                }
            } catch (e: Exception) {
                _currentGoalState.update { GoalUiState.Error }
            }
        }
    }

    private fun inProgress() {
        viewModelScope.launch {
            try {
                val pagedResponse = libraryBookService.search(
                    page = DEFAULT_PAGE,
                    size = DEFAULT_PAGE_SIZE,
                    status = LibraryBookStatusTypeDTO.IN_PROGRESS
                )
                val books = pagedResponse.content.toLibraryBookPreview()
                _inProgressState.update {
                    InProgressLibraryUiState.Success(
                        books = books,
                        currentPage = DEFAULT_PAGE
                    )
                }
            } catch (e: Exception) {
                _inProgressState.update { InProgressLibraryUiState.Error }
            }
        }
    }

    private fun toDnf(action: BookToDnf) {
        toStatus(
            libraryBookPreview = action.libraryBookPreview,
            dto = EditLibraryBookStatusDto(
                libraryBookStatusType = LibraryBookStatusTypeDTO.DNF,
            ),
            action
        )
    }

    private fun toRead(action: BookToRead) {
        toStatus(
            libraryBookPreview = action.libraryBookPreview,
            dto = EditLibraryBookStatusDto(
                libraryBookStatusType = LibraryBookStatusTypeDTO.READ,
                endDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
            ),
            action = action
        )
    }

    private fun toStatus(
        libraryBookPreview: LibraryBookPreview,
        dto: EditLibraryBookStatusDto,
        action: HomeAction
    ) {
        viewModelScope.launch {
            libraryBookService.editStatus(
                id = libraryBookPreview.id,
                dto = dto
            ).onSuccess {
                load()
                success(action)
            }.onFailure { e ->
                error(e.message ?: "Unknown error")
            }
        }
    }

    private fun setGoal(action: SetGoal) {
        val amount = action.amount
        viewModelScope.launch {
            try {
                when (val state = _currentGoalState.value) {
                    is GoalUiState.Success if state.goal != null -> {
                        goalService.set(
                            id = state.goal.id,
                            amount = amount
                        )
                    }
                    else -> {
                        goalService.create(
                            amount = amount
                        )
                    }
                }
                load()
                success(action)
            } catch (e: Exception) {
                _currentGoalState.update { GoalUiState.Error }
            }
        }
    }

    override fun act(action: HomeAction) {
        when(action) {
            is SetGoal -> setGoal(action)
            is BookToDnf -> toDnf(action)
            is BookToRead -> toRead(action)
        }
    }
}

sealed interface InProgressLibraryUiState {
    object Loading : InProgressLibraryUiState
    object Error : InProgressLibraryUiState
    data class Success(
        val books: List<LibraryBookPreview>,
        val currentPage: Int,
    ) : InProgressLibraryUiState
}

sealed interface GoalUiState {
    object Loading : GoalUiState
    object Error : GoalUiState
    data class Success(
        val goal: GoalPreview?
    ) : GoalUiState
}