package com.indigo.woorm.library.service

import com.indigo.woorm.books.service.dto.ShelfDto
import com.indigo.woorm.library.books.LibraryBook
import com.indigo.woorm.library.service.dto.CreateLibraryBookDto
import com.indigo.woorm.library.service.dto.CreateLibraryBookStatusDto
import com.indigo.woorm.library.service.dto.CreateLibraryShelf
import com.indigo.woorm.library.service.dto.EditLibraryBookStatusDto
import com.indigo.woorm.library.service.dto.GetLibraryBookDto
import com.indigo.woorm.library.service.dto.GetLibraryBooksDto
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.library.service.dto.UpdateLibraryBookCommentDto
import com.indigo.woorm.library.service.dto.UpdateLibraryShelfDto
import com.indigo.woorm.pagination.DEFAULT_PAGE
import com.indigo.woorm.pagination.DEFAULT_PAGE_SIZE
import com.indigo.woorm.pagination.PagedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource

class LibraryBookService(private val client: HttpClient) {

    suspend fun findById(id: Int): Result<GetLibraryBookDto> = runCatching {
        val response = client.get(Libraries.Books.Id(id = id))
        if (response.status != HttpStatusCode.OK) {
            error("Failed to load library book (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun create(id: Int, dto: CreateLibraryBookDto): Result<GetLibraryBookDto> = runCatching {
        val response = client.post(Libraries.Books.Id(id = id)) {
            setBody(dto)
        }
        if (response.status != HttpStatusCode.Created) {
            error("Failed to add book to library (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun createStatus(id: Int, dto: CreateLibraryBookStatusDto): Result<GetLibraryBookDto> = runCatching {
        val parentId = Libraries.Books.Id(id = id)
        val response = client.post(Libraries.Books.Id.Status(parent = parentId)) {
            setBody(dto)
        }
        if (response.status != HttpStatusCode.Created) {
            error("Failed to create status (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun editStatus(id: Int, dto: EditLibraryBookStatusDto): Result<GetLibraryBookDto> = runCatching {
        val parentId = Libraries.Books.Id(id = id)
        val response = client.patch(Libraries.Books.Id.Status(parent = parentId)) {
            setBody(dto)
        }
        if (response.status != HttpStatusCode.OK) {
            error("Failed to update status (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun deleteStatus(id: Int, statusId: Int): Result<GetLibraryBookDto> = runCatching {
        val parentId = Libraries.Books.Id(id = id)
        val response = client.delete(
            Libraries.Books.Id.Status.StatusId(
                parent = Libraries.Books.Id.Status(parent = parentId),
                statusId = statusId
            )
        )
        if (response.status != HttpStatusCode.OK) {
            error("Failed to delete status (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun updateComment(id: Int, dto: UpdateLibraryBookCommentDto): Result<GetLibraryBookDto> = runCatching {
        val parentId = Libraries.Books.Id(id = id)
        val response = client.put(Libraries.Books.Id.Comment(parent = parentId)) {
            setBody(dto)
        }
        if (response.status != HttpStatusCode.OK) {
            error("Failed to update comment (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun delete(id: Int) {
        client.delete(Libraries.Books.Id(id = id)).apply(::println)
    }

    suspend fun search(
        query: String? = null,
        page: Int,
        size: Int,
        status: LibraryBookStatusTypeDTO? = null,
        tbr: Boolean? = null,
        shelf: Int? = null,
    ): PagedResponse<GetLibraryBooksDto> {
        return client.get(
            Libraries.Books.All(
                q = query,
                page = page,
                size = size,
                status = status,
                tbr = tbr,
                shelf = shelf
            )
        ).body()
    }

    suspend fun toLibraryBookStatus(
        id: Int,
        libraryBook: LibraryBook?,
        libraryBookStatusType: LibraryBookStatusTypeDTO,
        endDate: String? = null,
    ): Result<GetLibraryBookDto> = when {
        libraryBook == null -> create(
            id = id,
            dto = CreateLibraryBookDto(
                libraryBookStatusType = libraryBookStatusType,
                endDate = endDate,
            )
        )

        libraryBook.currentStatus == null -> createStatus(
            id = id,
            dto = CreateLibraryBookStatusDto(
                libraryBookStatusType = libraryBookStatusType,
                endDate = endDate,
            )
        )

        else -> editStatus(
            id = id,
            dto = EditLibraryBookStatusDto(
                libraryBookStatusType = libraryBookStatusType,
                endDate = endDate,
            )
        )
    }


    suspend fun toLibrary(
        id: Int,
        libraryBook: LibraryBook?
    ): Result<GetLibraryBookDto?> = when {
        libraryBook == null -> create(
            id = id,
            dto = CreateLibraryBookDto(
                libraryBookStatusType = null
            )
        )
        else -> {
            delete(id = id)
            Result.success(null)
        }
    }

    suspend fun shelves() : List<ShelfDto> =
        client.get(Libraries.Shelves()).body()

    suspend fun deleteShelf(id: Int, shelfId: Int): Result<GetLibraryBookDto> = runCatching {
        val response = client.delete(
            Libraries.Books.Id.Shelves.ShelfId(
                parent = Libraries.Books.Id.Shelves(
                    parent = Libraries.Books.Id(
                        id = id
                    )
                ), shelfId = shelfId
            )
        )
        if (response.status != HttpStatusCode.OK) {
            error("Failed to remove shelf (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun addShelf(id: Int, shelfId: Int): Result<GetLibraryBookDto> = runCatching {
        val response = client.post(
            Libraries.Books.Id.Shelves.ShelfId(
                parent = Libraries.Books.Id.Shelves(
                    parent = Libraries.Books.Id(
                        id = id
                    )
                ), shelfId = shelfId
            )
        )
        if (response.status != HttpStatusCode.OK) {
            error("Failed to add shelf (${response.status.value})")
        }
        response.body<GetLibraryBookDto>()
    }

    suspend fun createShelf(name: String, bookId: Int? = null): Result<ShelfDto> = runCatching {
        val response = client.post(Libraries.Shelves()) {
            setBody(CreateLibraryShelf(name = name, bookId = bookId))
        }
        if (response.status != HttpStatusCode.Created) {
            error("Failed to create shelf (${response.status.value})")
        }
        response.body<ShelfDto>()
    }

    suspend fun updateShelf(shelfId: Int, name: String): Result<ShelfDto> = runCatching {
        val response = client.patch(Libraries.Shelves.ShelfId(shelfId = shelfId)) {
            setBody(UpdateLibraryShelfDto(name = name))
        }
        if (response.status != HttpStatusCode.OK) {
            error("Failed to rename shelf (${response.status.value})")
        }
        response.body<ShelfDto>()
    }

    suspend fun deleteShelf(shelfId: Int) {
        client.delete(Libraries.Shelves.ShelfId(shelfId = shelfId)).apply(::println)
    }

}

@Resource("libraries")
class Libraries {
    @Resource("books")
    class Books(val parent: Libraries = Libraries()) {
        @Resource("{id}")
        data class Id(val parent: Books = Books(), val id: Int) {
            @Resource("statuses")
            data class Status(val parent: Id) {
                @Resource("{statusId}")
                data class StatusId(val parent: Status, val statusId: Int)
            }
            @Resource("comment")
            data class Comment(val parent: Id)
            @Resource("shelves")
            class Shelves(val parent: Id) {
                @Resource("{shelfId}")
                data class ShelfId(val parent: Shelves, val shelfId: Int)
            }
        }
        @Resource("")
        data class All(
            val parent: Books = Books(),
            val q: String? = null,
            val page: Int = DEFAULT_PAGE,
            val size: Int = DEFAULT_PAGE_SIZE,
            val status: LibraryBookStatusTypeDTO? = null,
            val tbr: Boolean? = null,
            val shelf: Int? = null
        )
    }
    @Resource("shelves")
    class Shelves(val parent: Libraries = Libraries()) {
        @Resource("{shelfId}")
        data class ShelfId(val parent: Shelves = Shelves(), val shelfId: Int)
    }
}
