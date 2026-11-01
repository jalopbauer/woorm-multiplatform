package com.indigo.woorm.books.service

import com.indigo.woorm.books.service.dto.GetBookDto
import com.indigo.woorm.books.service.dto.GetBooksDto
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import com.indigo.woorm.pagination.PagedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource

class BookService(private val client: HttpClient) {
    suspend fun findById(id: Int): GetBookDto {
        return client.get(Books.Id(id = id)).body()
    }

    suspend fun search(
        query: String?,
        page: Int,
        size: Int,
    ): PagedResponse<GetBooksDto> {
        return client.get(Books.All(q = query, page = page, size = size)).body()
    }
}

@Resource("books")
class Books {
    @Resource("{id}")
    data class Id(val parent: Books = Books(), val id: Int)
    @Resource("")
    data class All(
        val parent: Books = Books(),
        val q: String? = null,
        val page: Int = DEFAULT_PAGE,
        val size: Int = DEFAULT_PAGE_SIZE,
    )
}