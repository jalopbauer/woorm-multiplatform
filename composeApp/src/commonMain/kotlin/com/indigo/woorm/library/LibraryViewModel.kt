package com.indigo.woorm.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.books.service.dto.toShelf
import com.indigo.woorm.common.search.SearchQueryPipeline
import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.library.filters.LibraryBookStatusFilter
import com.indigo.woorm.library.service.LibraryBookService
import com.indigo.woorm.library.service.dto.toLibraryBookPreview
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val libraryBookService: LibraryBookService,
    private val searchLibraryBooks: SearchLibraryBooksUseCase,
) : ViewModel() {
    private val _searchState = MutableStateFlow<LibraryUiState>(
        LibraryUiState.Loading(query = "", filter = LibraryBookStatusFilter.TBR)
    )
    val searchState: StateFlow<LibraryUiState> = _searchState.asStateFlow()

    private val _shelvesState = MutableStateFlow<LibraryShelvesUiState>(LibraryShelvesUiState.Loading)
    val shelvesState: StateFlow<LibraryShelvesUiState> = _shelvesState.asStateFlow()

    private val _expandedShelves = MutableStateFlow<Map<Int, ShelfBooksUiState>>(emptyMap())
    val expandedShelves: StateFlow<Map<Int, ShelfBooksUiState>> = _expandedShelves.asStateFlow()

    private val searchPipeline = SearchQueryPipeline(viewModelScope, ::executeSearch)

    init {
        viewModelScope.launch {
            runSearch(query = "", filter = LibraryBookStatusFilter.TBR)
        }
        loadShelves()
    }

    fun onQueryChange(query: String) {
        searchPipeline.onQueryChange(query)
    }

    fun submit(query: String) {
        val state = _searchState.value
        if (state is LibraryUiState.Loading && state.query == query) return
        if (state is LibraryUiState.Success && state.query == query) return
        searchPipeline.submit(query)
    }

    private suspend fun executeSearch(query: String) {
        val state = _searchState.value
        if (state is LibraryUiState.Loading && state.query == query) return
        if (state is LibraryUiState.Success && state.query == query) return
        runSearch(query = query, filter = state.filter)
    }

    private suspend fun runSearch(query: String, filter: LibraryBookStatusFilter?) {
        _searchState.value = LibraryUiState.Loading(query = query, filter = filter)
        searchLibraryBooks(
            query = query,
            page = DEFAULT_PAGE,
            size = DEFAULT_PAGE_SIZE,
            status = filter?.toStatusDto(),
            tbr = filter?.isTbr == true
        ).onSuccess { pagedResponse ->
            val books = pagedResponse.content
            _searchState.update {
                if (it.query == query && it.filter == filter) {
                    LibraryUiState.Success(
                        books = books,
                        query = query,
                        currentPage = DEFAULT_PAGE,
                        filter = filter
                    )
                } else {
                    it
                }
            }
        }.onFailure { throwable ->
            _searchState.update {
                if (it.query == query && it.filter == filter) {
                    LibraryUiState.Error(
                        query = query,
                        filter = filter,
                        errorMessage = throwable.message
                    )
                } else {
                    it
                }
            }
        }
    }

    fun filter(filter: LibraryBookStatusFilter?) {
        viewModelScope.launch {
            val state = _searchState.value
            runSearch(query = state.query, filter = filter)
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            val state = _searchState.value
            if (state is LibraryUiState.Success && state.books.size == DEFAULT_PAGE_SIZE) {
                val query = state.query
                val filter = state.filter
                val page = state.currentPage + 1
                searchLibraryBooks(
                    query = query,
                    page = page,
                    size = DEFAULT_PAGE_SIZE,
                    status = filter?.toStatusDto(),
                    tbr = filter?.isTbr == true
                ).onSuccess { pagedResponse ->
                    val books = pagedResponse.content
                    _searchState.update {
                        if (it is LibraryUiState.Success && it.query == query && it.filter == filter && it.currentPage == page - 1) {
                            it.copy(books = it.books + books, currentPage = page)
                        } else {
                            it
                        }
                    }
                }.onFailure { throwable ->
                    _searchState.update {
                        if (it is LibraryUiState.Success && it.query == query && it.filter == filter && it.currentPage == page - 1) {
                            LibraryUiState.Error(
                                query = query,
                                filter = filter,
                                errorMessage = throwable.message
                            )
                        } else {
                            it
                        }
                    }
                }
            }
        }
    }

    fun loadShelves() {
        viewModelScope.launch {
            _shelvesState.update { LibraryShelvesUiState.Loading }
            runCatching {
                libraryBookService.shelves().toShelf()
            }.onSuccess { shelves ->
                _shelvesState.update {
                    LibraryShelvesUiState.Success(shelves.filter { it.bookCount > 0 })
                }
            }.onFailure {
                _shelvesState.update { LibraryShelvesUiState.Error }
            }
        }
    }

    fun toggleShelf(shelf: LibraryBookShelf) {
        if (_expandedShelves.value.containsKey(shelf.id)) {
            _expandedShelves.update { it - shelf.id }
        } else {
            loadShelfBooks(shelf, page = DEFAULT_PAGE)
        }
    }

    fun loadNextShelfPage(shelf: LibraryBookShelf) {
        val state = _expandedShelves.value[shelf.id] as? ShelfBooksUiState.Success ?: return
        if (state.isLoadingMore || state.books.size >= state.totalElements) return
        loadShelfBooks(shelf, page = state.currentPage + 1)
    }

    private fun loadShelfBooks(shelf: LibraryBookShelf, page: Int) {
        viewModelScope.launch {
            if (page == DEFAULT_PAGE) {
                _expandedShelves.update { it + (shelf.id to ShelfBooksUiState.Loading) }
            } else {
                markExpandedShelfLoadingMore(shelf.id, isLoadingMore = true)
            }
            runCatching {
                libraryBookService.search(
                    page = page,
                    size = DEFAULT_PAGE_SIZE,
                    shelf = shelf.id
                )
            }.onSuccess { pagedResponse ->
                val books = pagedResponse.content.toLibraryBookPreview()
                _expandedShelves.update { expanded ->
                    expanded + (
                        shelf.id to ShelfBooksUiState.Success(
                            books = (expanded[shelf.id] as? ShelfBooksUiState.Success)?.books.orEmpty() + books,
                            currentPage = page,
                            totalElements = pagedResponse.page.totalElements,
                        )
                        )
                }
            }.onFailure {
                if (page == DEFAULT_PAGE) {
                    _expandedShelves.update { it + (shelf.id to ShelfBooksUiState.Error) }
                } else {
                    markExpandedShelfLoadingMore(shelf.id, isLoadingMore = false)
                }
            }
        }
    }

    private fun markExpandedShelfLoadingMore(shelfId: Int, isLoadingMore: Boolean) {
        _expandedShelves.update { expanded ->
            val state = expanded[shelfId] as? ShelfBooksUiState.Success
                ?: return@update expanded
            expanded + (shelfId to state.copy(isLoadingMore = isLoadingMore))
        }
    }
}

sealed interface LibraryUiState {
    val query: String
    val filter: LibraryBookStatusFilter?
    data class Loading(
        override val query: String,
        override val filter: LibraryBookStatusFilter?
    ) : LibraryUiState
    data class Error(
        override val query: String,
        override val filter: LibraryBookStatusFilter?,
        val errorMessage: String?
    ) : LibraryUiState
    data class Success(
        val books: List<LibraryBookPreview>,
        override val query: String,
        val currentPage: Int,
        override val filter: LibraryBookStatusFilter?
    ) : LibraryUiState
}

sealed interface LibraryShelvesUiState {
    data object Loading : LibraryShelvesUiState
    data object Error : LibraryShelvesUiState
    data class Success(val shelves: List<LibraryBookShelf>) : LibraryShelvesUiState
}

sealed interface ShelfBooksUiState {
    data object Loading : ShelfBooksUiState
    data object Error : ShelfBooksUiState
    data class Success(
        val books: List<LibraryBookPreview>,
        val currentPage: Int,
        val totalElements: Int,
        val isLoadingMore: Boolean = false,
    ) : ShelfBooksUiState
}
