package com.indigo.woorm.books.search

import com.indigo.woorm.books.BookPreview
import com.indigo.woorm.books.service.BookService
import com.indigo.woorm.books.service.dto.GetBooksDto
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import com.indigo.woorm.pagination.PagedResponse

class SearchBooksUseCase(
    private val bookService: BookService,
) {
    suspend operator fun invoke(
        query: String,
        page: Int = DEFAULT_PAGE,
        size: Int = DEFAULT_PAGE_SIZE,
    ): Result<PagedResponse<BookPreview>> = runCatching {
        bookService.search(query, page, size)
    }.map { response ->
        PagedResponse(
            content = response.content.map { it.toBookPreview() },
            links = response.links,
            page = response.page,
        )
    }
}

private fun GetBooksDto.toBookPreview(): BookPreview =
    BookPreview(
        id = this.id,
        title = this.title,
        coverUrl = this.coverUrl,
        authors = listOf(),
    )
