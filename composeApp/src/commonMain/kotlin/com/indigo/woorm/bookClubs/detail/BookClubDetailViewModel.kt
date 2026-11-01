package com.indigo.woorm.bookClubs.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.indigo.woorm.bookClubs.service.BookClubService
import com.indigo.woorm.common.book.Book
import com.indigo.woorm.common.search.SearchQueryPipeline
import com.indigo.woorm.library.SearchLibraryBooksUseCase
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.library.service.LibraryBookService
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.library.service.dto.toLibraryBook
import com.indigo.woorm.navigation.Route
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import com.indigo.woorm.users.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.todayIn
import kotlinx.datetime.TimeZone

class BookClubDetailViewModel(
    private val bookClubService: BookClubService,
    private val libraryBookService: LibraryBookService,
    private val searchLibraryBooks: SearchLibraryBooksUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val id = savedStateHandle.toRoute<Route.BookClub>().id
    private var _bookClub = MutableStateFlow<BookClubState>(BookClubState.Loading)
    val bookClub = _bookClub.asStateFlow()

    private val _isStartingBook = MutableStateFlow(false)
    val isStartingBook = _isStartingBook.asStateFlow()

    private val _isFinishingBook = MutableStateFlow(false)
    val isFinishingBook = _isFinishingBook.asStateFlow()

    init {
        bookClub()
    }

    fun bookClub() {
        viewModelScope.launch {
            _bookClub.emit(BookClubState.Loading)
            refreshBookClub()
        }
    }

    private suspend fun refreshBookClub() {
        bookClubService.findById(id).onSuccess { bookClub ->
            val currentUserId = userPreferencesRepository.getUserId()
            _bookClub.emit(
                BookClubState.Success(
                    bookClub.copy(
                        memberBookStatuses = bookClub.memberBookStatuses.filter { it.userId != currentUserId }
                    )
                )
            )
        }
    }

    suspend fun startCurrentBook(): Boolean {
        if (_isStartingBook.value) return false
        val state = _bookClub.value
        if (state !is BookClubState.Success) return false
        val bookId = state.bookClub.currentBook?.id ?: return false
        _isStartingBook.update { true }
        val libraryBook = libraryBookService.findById(bookId).getOrNull()?.toLibraryBook()
        val result = libraryBookService.toLibraryBookStatus(
            id = bookId,
            libraryBook = libraryBook,
            libraryBookStatusType = LibraryBookStatusTypeDTO.IN_PROGRESS,
        )
        if (result.isSuccess) refreshBookClub()
        _isStartingBook.update { false }
        return result.isSuccess
    }

    suspend fun finishCurrentBook(): Boolean {
        if (_isFinishingBook.value) return false
        val state = _bookClub.value
        if (state !is BookClubState.Success) return false
        val bookId = state.bookClub.currentBook?.id ?: return false
        _isFinishingBook.update { true }
        val libraryBook = libraryBookService.findById(bookId).getOrNull()?.toLibraryBook()
        val result = libraryBookService.toLibraryBookStatus(
            id = bookId,
            libraryBook = libraryBook,
            libraryBookStatusType = LibraryBookStatusTypeDTO.READ,
            endDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString(),
        )
        if (result.isSuccess) refreshBookClub()
        _isFinishingBook.update { false }
        return result.isSuccess
    }

    fun removeCurrentBook() {
        viewModelScope.launch {
            bookClubService.removeCurrentBook(id)
            bookClub()
        }
    }

    private val _bookSearchState = MutableStateFlow<BookSearchUiState>(BookSearchUiState.Idle)
    val bookSearchState: StateFlow<BookSearchUiState> = _bookSearchState.asStateFlow()

    private var bookSearchPipeline: SearchQueryPipeline? = null

    fun startBookSearch() {
        stopBookSearch()
        bookSearchPipeline = SearchQueryPipeline(viewModelScope, ::executeBookSearch)
            .also { pipeline ->
                pipeline.submit("")
            }
    }

    fun stopBookSearch() {
        bookSearchPipeline?.cancel()
        bookSearchPipeline = null
        _bookSearchState.value = BookSearchUiState.Idle
    }

    fun onBookSearchQueryChange(query: String) {
        bookSearchPipeline?.onQueryChange(query)
    }

    fun submitBookSearch(query: String) {
        val state = _bookSearchState.value
        if (state is BookSearchUiState.Loading && state.query == query) return
        if (state is BookSearchUiState.Success && state.query == query) return
        if (state is BookSearchUiState.Empty && state.query == query) return
        bookSearchPipeline?.submit(query)
    }

    private suspend fun executeBookSearch(query: String) {
        val state = _bookSearchState.value
        if (state is BookSearchUiState.Loading && state.query == query) return
        if (state is BookSearchUiState.Success && state.query == query) return
        if (state is BookSearchUiState.Empty && state.query == query) return
        runBookSearch(query)
    }

    private suspend fun runBookSearch(query: String) {
        _bookSearchState.value = BookSearchUiState.Loading(query)
        searchLibraryBooks(
            query,
            page = DEFAULT_PAGE,
            size = DEFAULT_PAGE_SIZE
        ).onSuccess { pagedResponse ->
            val books = pagedResponse.content
            _bookSearchState.update {
                if (it is BookSearchUiState.Loading && it.query == query) {
                    if (books.isEmpty()) {
                        BookSearchUiState.Empty(query)
                    } else {
                        BookSearchUiState.Success(query, books)
                    }
                } else {
                    it
                }
            }
        }.onFailure { throwable ->
            _bookSearchState.update {
                if (it is BookSearchUiState.Loading && it.query == query) {
                    BookSearchUiState.Error(query, throwable.message)
                } else {
                    it
                }
            }
        }
    }

    fun setCurrentBook(book: Book) {
        viewModelScope.launch {
            bookClubService.setCurrentBook(id, book.id)
            bookClub()
        }
    }

    suspend fun ensureInvite(): Invite? {
        val state = _bookClub.value
        if (state is BookClubState.Success){
            val current = state.bookClub
            current.invite?.let { return it }
            return bookClubService.createInvite(id).onSuccess { invite ->
                _bookClub.update {
                    BookClubState.Success(
                        current.copy(invite = invite)
                    )
                }
            }.getOrNull()
        }
        return null
    }

}

sealed interface BookClubState {
    data object Loading: BookClubState
    data object NotFound: BookClubState
    data class Success(
        val bookClub: BookClubDetail,
    ) : BookClubState
}

sealed interface BookSearchUiState {
    data object Idle : BookSearchUiState
    data class Loading(val query: String) : BookSearchUiState
    data class Empty(val query: String) : BookSearchUiState
    data class Error(val query: String, val errorMessage: String?) : BookSearchUiState
    data class Success(
        val query: String,
        val books: List<LibraryBookPreview>,
    ) : BookSearchUiState
}
