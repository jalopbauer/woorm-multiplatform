package com.indigo.woorm.books.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.books.BookPreview
import com.indigo.woorm.common.search.SearchQueryPipeline
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchBooks: SearchBooksUseCase
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    private val searchPipeline = SearchQueryPipeline(viewModelScope, ::executeSearch)

    fun onQueryChange(query: String) {
        searchPipeline.onQueryChange(query)
    }

    fun submit(query: String) {
        val sanitizedQuery = query.trim()
        val state = _searchState.value
        if (state.query == sanitizedQuery && state.status in NO_OP_STATUSES) return
        searchPipeline.submit(query)
    }

    private suspend fun executeSearch(query: String) {
        val sanitizedQuery = query.trim()
        if (sanitizedQuery.isEmpty()) {
            _searchState.value = SearchUiState()
            return
        }

        val state = _searchState.value
        if (state.query == sanitizedQuery && state.status in NO_OP_STATUSES) return

        _searchState.update {
            it.copy(
                query = sanitizedQuery,
                status = SearchUiState.Status.Loading,
                currentPage = DEFAULT_PAGE,
                canLoadMore = false,
                isAppending = false,
                errorMessage = null
            )
        }

        searchBooks(
            sanitizedQuery,
            page = DEFAULT_PAGE,
            size = DEFAULT_PAGE_SIZE
        ).onSuccess { pagedResponse ->
            val books = pagedResponse.content
            _searchState.update {
                if (it.query != sanitizedQuery) {
                    it
                } else {
                    it.copy(
                        books = books,
                        status = if (books.isEmpty()) {
                            SearchUiState.Status.Empty
                        } else {
                            SearchUiState.Status.Success
                        },
                        currentPage = pagedResponse.page.page,
                        canLoadMore = pagedResponse.links.next != null,
                        isAppending = false,
                        errorMessage = null
                    )
                }
            }
        }.onFailure { throwable ->
            _searchState.update {
                if (it.query != sanitizedQuery) {
                    it
                } else {
                    it.copy(
                        status = SearchUiState.Status.Error,
                        isAppending = false,
                        canLoadMore = false,
                        errorMessage = throwable.message
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        val current = _searchState.value
        if (current.status != SearchUiState.Status.Success || !current.canLoadMore || current.isAppending) {
            return
        }
        val query = current.query
        val nextPage = current.currentPage + 1

        viewModelScope.launch {
            _searchState.update {
                if (it.query == query) {
                    it.copy(isAppending = true, errorMessage = null)
                } else {
                    it
                }
            }

            searchBooks(
                query,
                page = nextPage,
                size = DEFAULT_PAGE_SIZE
            ).onSuccess { pagedResponse ->
                val books = pagedResponse.content
                _searchState.update {
                    if (it.query != query) {
                        it
                    } else {
                        it.copy(
                            books = it.books + books,
                            status = SearchUiState.Status.Success,
                            currentPage = pagedResponse.page.page,
                            canLoadMore = pagedResponse.links.next != null,
                            isAppending = false,
                            errorMessage = null
                        )
                    }
                }
            }.onFailure { throwable ->
                _searchState.update {
                    if (it.query != query) {
                        it
                    } else {
                        it.copy(
                            isAppending = false,
                            canLoadMore = false,
                            errorMessage = throwable.message
                        )
                    }
                }
            }
        }
    }

    private companion object {
        val NO_OP_STATUSES = setOf(
            SearchUiState.Status.Loading,
            SearchUiState.Status.Success,
            SearchUiState.Status.Empty
        )
    }
}

data class SearchUiState(
    val query: String = "",
    val books: List<BookPreview> = emptyList(),
    val status: Status = Status.Idle,
    val currentPage: Int = DEFAULT_PAGE,
    val canLoadMore: Boolean = false,
    val isAppending: Boolean = false,
    val errorMessage: String? = null,
) {
    enum class Status {
        Idle,
        Loading,
        Success,
        Empty,
        Error
    }
}
