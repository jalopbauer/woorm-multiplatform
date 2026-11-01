package com.indigo.woorm.bookClubs.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.bookClubs.data.BookClubData
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import com.indigo.woorm.bookClubs.service.BookClubService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookClubsViewModel(
    private val bookClubService: BookClubService,
): ViewModel() {

    private var _bookClubPreviews = MutableStateFlow<BookClubsState>(BookClubsState.Loading)
    val bookClubsState = _bookClubPreviews.asStateFlow()

    init {
        allBookClubs()
    }

    private fun allBookClubs() {
        viewModelScope.launch {
            bookClubService.all().onSuccess {
                _bookClubPreviews.emit(BookClubsState.Success(it))
            }
        }
    }

    fun createBookClub(bookClubData: BookClubData) {
        viewModelScope.launch {
            _bookClubPreviews.update { BookClubsState.Loading}
            bookClubService.create(bookClubData)
                .onSuccess {
                    allBookClubs()
                }.onFailure {

                }
        }

    }
}

sealed interface BookClubsState {
    data object Loading: BookClubsState
    data class Success(
        val bookClubs: List<BookClubPreview>
    ) : BookClubsState
}