package com.indigo.woorm.library

import com.indigo.woorm.library.books.LibraryBookPreview
import com.indigo.woorm.library.service.LibraryBookService
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.library.service.dto.toLibraryBookPreview
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import com.indigo.woorm.pagination.PagedResponse

class SearchLibraryBooksUseCase(
    private val libraryBookService: LibraryBookService,
) {
    suspend operator fun invoke(
        query: String? = null,
        page: Int = DEFAULT_PAGE,
        size: Int = DEFAULT_PAGE_SIZE,
        status: LibraryBookStatusTypeDTO? = null,
        tbr: Boolean? = null,
        shelf: Int? = null,
    ): Result<PagedResponse<LibraryBookPreview>> = runCatching {
        libraryBookService.search(
            query = query,
            page = page,
            size = size,
            status = status,
            tbr = tbr,
            shelf = shelf,
        )
    }.map { response ->
        PagedResponse(
            content = response.content.toLibraryBookPreview(),
            links = response.links,
            page = response.page,
        )
    }
}
