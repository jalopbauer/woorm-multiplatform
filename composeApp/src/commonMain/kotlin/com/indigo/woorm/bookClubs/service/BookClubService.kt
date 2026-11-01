package com.indigo.woorm.bookClubs.service

import com.indigo.woorm.bookClubs.data.BookClubData
import com.indigo.woorm.bookClubs.detail.BookClubDetail
import com.indigo.woorm.bookClubs.detail.Invite
import com.indigo.woorm.bookClubs.detail.InviteDetail
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class BookClubService(private val client: HttpClient) {
    suspend fun create(bookClubData: BookClubData): Result<Unit> =
        runCatching {
            val post: HttpResponse = client.post(BookClubs()) {
                setBody(bookClubData)
            }
            when (post.status) {
                HttpStatusCode.Created -> Unit
                else -> throw RuntimeException("Error creating book club")
            }
        }

    suspend fun all(): Result<List<BookClubPreview>> =
        runCatching {
            val response = client.get(BookClubs())
            when (response.status) {
                HttpStatusCode.OK -> response.body()
                else -> throw RuntimeException("Error creating book club")
            }
        }

    suspend fun findById(id: String): Result<BookClubDetail> =
        runCatching {
            val response = client.get(BookClubs.Id(id = id))
            when (response.status) {
                HttpStatusCode.OK -> response.body()
                else -> throw RuntimeException("Error creating book club")
            }
        }

    suspend fun removeCurrentBook(id: String): Result<Unit> =
        runCatching {
            val response = client.delete(BookClubs.Id.CurrentBook(parent = BookClubs.Id(id = id)))
            when (response.status) {
                HttpStatusCode.OK -> Unit
                else -> throw RuntimeException("Error creating book club")
            }
        }

    suspend fun setCurrentBook(bookClubId: String, bookId: Int) : Result<Unit> =
        runCatching {
            val response = client.post(
                BookClubs.Id.CurrentBook.Id(
                    parent = BookClubs.Id.CurrentBook(
                        parent = BookClubs.Id(id = bookClubId)
                    ), bookId = bookId
                )
            )
            when (response.status) {
                HttpStatusCode.OK -> Unit
                else -> throw RuntimeException("Error creating book club")
            }
        }

    suspend fun createInvite(bookClubId: String): Result<Invite> =
        runCatching {
            val response = client.post(
                BookClubs.Id.Invite(
                    parent = BookClubs.Id(id = bookClubId)
                )
            )
            when (response.status) {
                HttpStatusCode.Created -> response.body()
                else -> throw RuntimeException("Error creating book club invite")
            }
        }

    suspend fun invite(inviteId: Int) : Result<InviteDetail> =
        runCatching {
            val response = client.get(
                BookClubs.Invite.Id(
                    parent = BookClubs.Invite(
                        parent = BookClubs()
                    ),
                    inviteId = inviteId
                )
            )
            when (response.status) {
                HttpStatusCode.OK -> response.body()
                else -> throw RuntimeException("Error creating book club")
            }
        }

    suspend fun join(inviteId: Int) : Result<Unit> =
        runCatching {
            val response = client.post(
                BookClubs.Join.Id(
                    id = inviteId
                )
            )
            when (response.status) {
                HttpStatusCode.Created -> Unit
                else -> throw RuntimeException("Error creating book club")
            }
        }
}
