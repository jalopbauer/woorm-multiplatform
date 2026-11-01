package com.indigo.woorm.shelves.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.books.service.dto.ShelfDto
import com.indigo.woorm.books.service.dto.toShelf
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.library.service.LibraryBookService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShelvesViewModel(
    private val libraryBookService: LibraryBookService,
): ViewModel() {

    private var _shelvesState = MutableStateFlow<ShelvesState>(ShelvesState.Loading)
    val shelvesState = _shelvesState.asStateFlow()

    init {
        loadShelves()
    }

    fun loadShelves() {
        viewModelScope.launch {
            _shelvesState.update { ShelvesState.Loading }
            runCatching {
                libraryBookService.shelves().toShelf()
            }.onSuccess {
                _shelvesState.emit(ShelvesState.Success(it))
            }
        }
    }

    fun renameShelf(shelf: LibraryBookShelf, name: String) {
        viewModelScope.launch {
            libraryBookService.updateShelf(shelfId = shelf.id, name = name)
            loadShelves()
        }
    }

    fun createShelf(name: String) {
        viewModelScope.launch {
            libraryBookService.createShelf(name = name)
            loadShelves()
        }
    }

    fun deleteShelf(shelf: LibraryBookShelf) {
        viewModelScope.launch {
            libraryBookService.deleteShelf(shelfId = shelf.id)
            loadShelves()
        }
    }
}

sealed interface ShelvesState {
    data object Loading: ShelvesState
    data class Success(
        val shelves: List<LibraryBookShelf>
    ) : ShelvesState
}
