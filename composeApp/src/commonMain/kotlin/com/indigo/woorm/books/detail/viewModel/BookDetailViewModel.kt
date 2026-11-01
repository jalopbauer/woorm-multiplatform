package com.indigo.woorm.books.detail.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.books.detail.viewModel.action.BookDetailAction
import com.indigo.woorm.books.detail.viewModel.action.BookDnf
import com.indigo.woorm.books.detail.viewModel.action.BookInProgress
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
import com.indigo.woorm.books.service.BookService
import com.indigo.woorm.books.service.dto.toBookDetail
import com.indigo.woorm.books.service.dto.toShelf
import com.indigo.woorm.library.books.LibraryBook
import com.indigo.woorm.library.books.shelves.LibraryShelf
import com.indigo.woorm.library.books.status.LibraryBookStatus
import com.indigo.woorm.library.service.LibraryBookService
import com.indigo.woorm.library.service.dto.CreateLibraryBookStatusDto
import com.indigo.woorm.library.service.dto.EditLibraryBookStatusDto
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.library.service.dto.UpdateLibraryBookCommentDto
import com.indigo.woorm.library.service.dto.GetLibraryBookDto
import com.indigo.woorm.library.service.dto.toLibraryBook
import com.indigo.woorm.navigation.Route
import com.indigo.woorm.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.todayIn
import kotlinx.datetime.TimeZone

sealed interface BookUiState {
    data object Loading : BookUiState
    data class Success(val book: BookDetail) : BookUiState
    data class Error(val message: String) : BookUiState
}

sealed interface LibraryBookUiState {
    data object Loading : LibraryBookUiState
    data class Editing(val bookDetailAction: BookDetailAction?, val libraryBook: LibraryBook?) : LibraryBookUiState
    data class Success(
        val libraryBook: LibraryBook?,
        val shelves: List<LibraryShelf>,
    ) : LibraryBookUiState
    data class Error(val message: String) : LibraryBookUiState
}

class BookDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookService: BookService,
    private val libraryBookService: LibraryBookService,
) : BaseViewModel<BookDetailAction>() {
    private val id = savedStateHandle.toRoute<Route.BookDetail>().id
    private val _bookUiState = MutableStateFlow<BookUiState>(BookUiState.Loading)
    val bookUiState: StateFlow<BookUiState> = _bookUiState.asStateFlow()

    private val _libraryBookUiState = MutableStateFlow<LibraryBookUiState>(LibraryBookUiState.Editing(
        bookDetailAction = null,
        libraryBook = null
    ))
    val libraryBookUiState: StateFlow<LibraryBookUiState> = _libraryBookUiState.asStateFlow()

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch {
            try {
                val bookDto = bookService.findById(id).toBookDetail()
                _bookUiState.update { BookUiState.Success(book = bookDto) }
                refreshLibraryBook()
            } catch (e: Exception) {
                _bookUiState.update { BookUiState.Error(e.message ?: "Unknown error") }
            }
        }
    }

    private fun refreshLibraryBook() {
        viewModelScope.launch {
            try {
                val libraryBook = libraryBookService.findById(id).getOrNull()?.toLibraryBook()
                val shelves = libraryBookService.shelves()
                _libraryBookUiState.update {
                    LibraryBookUiState.Success(
                        libraryBook = libraryBook,
                        shelves = shelves.map { shelfDto ->
                            LibraryShelf(
                                id = shelfDto.id,
                                name = shelfDto.name,
                                selected = libraryBook?.shelves?.contains(shelfDto.toShelf()) ?: false
                            )
                        },
                    )
                }
            } catch (e: Exception) {
                _libraryBookUiState.update { LibraryBookUiState.Error(e.message ?: "Unknown error") }
            }
        }
    }

    private fun applyMutation(result: Result<GetLibraryBookDto?>, action: BookDetailAction) {
        viewModelScope.launch {
            result
                .onSuccess { response ->
                    val libraryBook = response?.toLibraryBook()
                    val shelves = libraryBookService.shelves()
                    _libraryBookUiState.update {
                        LibraryBookUiState.Success(
                            libraryBook = libraryBook,
                            shelves = shelves.map { shelfDto ->
                                LibraryShelf(
                                    id = shelfDto.id,
                                    name = shelfDto.name,
                                    selected = libraryBook?.shelves?.contains(shelfDto.toShelf()) ?: false
                                )
                            },
                        )
                    }
                    success(action)
                }
                .onFailure { e ->
                    refreshLibraryBook()
                    error(e.message ?: "Unknown error")
                }
        }
    }

    private fun toInProgress(action: BookInProgress) {
        toLibraryBookStatus(
            libraryBookStatusType = LibraryBookStatusTypeDTO.IN_PROGRESS,
            action = action,
        )
    }

    private fun toRead(action: BookRead) {
        toLibraryBookStatus(
            libraryBookStatusType = LibraryBookStatusTypeDTO.READ,
            action = action,
            endDate = if (action.finishedToday) Clock.System.todayIn(TimeZone.currentSystemDefault()).toString() else null,
        )
    }

    private fun toDnf(action: BookDnf) {
        toLibraryBookStatus(
            libraryBookStatusType = LibraryBookStatusTypeDTO.DNF,
            action = action,
        )
    }

    private fun toLibraryBookStatus(
        libraryBookStatusType: LibraryBookStatusTypeDTO,
        action: BookDetailAction,
        endDate: String? = null,
    ) {
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success) {
            if (state.libraryBook?.currentStatus?.type == libraryBookStatusType) return
            viewModelScope.launch {
                _libraryBookUiState.update {
                    LibraryBookUiState.Editing(
                        bookDetailAction = action,
                        libraryBook = state.libraryBook
                    )
                }
                val response = libraryBookService.toLibraryBookStatus(
                    id = id,
                    libraryBook = state.libraryBook,
                    libraryBookStatusType = libraryBookStatusType,
                    endDate = endDate,
                )
                applyMutation(response, action)
            }
        }
    }

    private fun toLibrary(action: ToLibrary) {
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success) {
            _libraryBookUiState.update {
                LibraryBookUiState.Editing(
                    bookDetailAction = action,
                    libraryBook = state.libraryBook
                )
            }
            viewModelScope.launch {
                val response = libraryBookService.toLibrary(
                    id = id,
                    libraryBook = state.libraryBook
                )
                applyMutation(response, action)
            }
        }
    }

    private fun deleteStatusHistory(action: DeleteStatusHistory) {
        val statusId = action.status.id ?: return
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success && state.libraryBook != null) {
            viewModelScope.launch {
                _libraryBookUiState.update {
                    LibraryBookUiState.Editing(
                        bookDetailAction = action,
                        libraryBook = state.libraryBook
                    )
                }
                val response = libraryBookService.deleteStatus(id = id, statusId = statusId)
                applyMutation(response, action)
            }
        }
    }

    private fun readAgain(action: ReadAgain) {
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success) {
            viewModelScope.launch {
                _libraryBookUiState.update {
                    LibraryBookUiState.Editing(
                        bookDetailAction = action,
                        libraryBook = state.libraryBook
                    )
                }
                val response = libraryBookService.createStatus(
                    id = id,
                    dto = CreateLibraryBookStatusDto(
                        libraryBookStatusType = LibraryBookStatusTypeDTO.IN_PROGRESS
                    )
                )
                applyMutation(response, action)
            }
        }
    }

    private fun toggleShelf(action: ToggleShelf) {
        val libraryShelf = action.libraryShelf
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success) {
            _libraryBookUiState.update {
                LibraryBookUiState.Editing(
                    bookDetailAction = action,
                    libraryBook = state.libraryBook
                )
            }
            viewModelScope.launch {
                val response = if (libraryShelf.selected) {
                    libraryBookService.deleteShelf(id = id, shelfId = libraryShelf.id)
                } else {
                    libraryBookService.addShelf(id = id, shelfId = libraryShelf.id)
                }
                applyMutation(response, action)
            }
        }
    }

    private fun createShelf(action: CreateShelf) {
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success) {
            _libraryBookUiState.update {
                LibraryBookUiState.Editing(
                    bookDetailAction = action,
                    libraryBook = state.libraryBook
                )
            }
            viewModelScope.launch {
                libraryBookService.createShelf(name = action.newShelfName, bookId = id)
                    .onSuccess {
                        refreshLibraryBook()
                        success(action)
                    }
                    .onFailure { e ->
                        refreshLibraryBook()
                        error(e.message ?: "Unknown error")
                    }
            }
        }
    }

    private fun rate(action: Rate) {
        val rating = action.rating
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success && state.libraryBook?.currentStatus is LibraryBookStatus.Read) {
            _libraryBookUiState.update {
                LibraryBookUiState.Editing(
                    bookDetailAction = action,
                    libraryBook = state.libraryBook
                )
            }
            viewModelScope.launch {
                val response = libraryBookService.editStatus(
                    id,
                    dto = EditLibraryBookStatusDto(
                        userRating = if (rating == state.libraryBook.currentStatus.userRating) 0.0 else rating
                    )
                )
                applyMutation(response, action)
            }
        }
    }

    private fun setStartDate(action: SetStartDate) {
        val date = action.date
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success) {
            viewModelScope.launch {
                val response = libraryBookService.editStatus(
                    id,
                    dto = EditLibraryBookStatusDto(
                        startDate = date?.toString()
                    )
                )
                applyMutation(response, action)
            }
        }
    }

    private fun setEndDate(action: SetEndDate) {
        val date = action.date
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success) {
            viewModelScope.launch {
                val response = libraryBookService.editStatus(
                    id,
                    dto = EditLibraryBookStatusDto(
                        endDate = date?.toString()
                    )
                )
                applyMutation(response, action)
            }
        }
    }

    private fun saveComment(action: SaveComment) {
        val state = _libraryBookUiState.value
        if (state is LibraryBookUiState.Success && state.libraryBook != null) {
            _libraryBookUiState.update {
                LibraryBookUiState.Editing(
                    bookDetailAction = action,
                    libraryBook = state.libraryBook
                )
            }
            viewModelScope.launch {
                val response = libraryBookService.updateComment(
                    id = id,
                    dto = UpdateLibraryBookCommentDto(
                        comment = action.comment
                    )
                )
                applyMutation(response, action)
            }
        }
    }

    override fun act(action: BookDetailAction) {
        when (action) {
            is BookInProgress -> toInProgress(action)
            is BookRead -> toRead(action)
            is BookDnf -> toDnf(action)
            is ReadAgain -> readAgain(action)
            is DeleteStatusHistory -> deleteStatusHistory(action)
            is ToggleShelf -> toggleShelf(action)
            is CreateShelf -> createShelf(action)
            is Rate -> rate(action)
            is ToLibrary -> toLibrary(action)
            is SetEndDate -> setEndDate(action)
            is SetStartDate -> setStartDate(action)
            is SaveComment -> saveComment(action)
        }
    }
}
