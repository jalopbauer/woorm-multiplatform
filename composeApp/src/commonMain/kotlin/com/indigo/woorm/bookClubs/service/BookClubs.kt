package com.indigo.woorm.bookClubs.service

import io.ktor.resources.Resource

@Resource("book-clubs")
class BookClubs {
    @Resource("invites")
    data class Invite(val parent: BookClubs) {
        @Resource("{inviteId}")
        data class Id(val parent: Invite, val inviteId: Int)
    }
    @Resource("{id}")
    data class Id(val parent: BookClubs = BookClubs(), val id: String) {
        @Resource("invites")
        data class Invite(val parent: Id)
        @Resource("current-book")
        data class CurrentBook(val parent: BookClubs.Id) {
            @Resource("{bookId}")
            data class Id(val parent: CurrentBook, val bookId: Int)
        }
    }
    @Resource("join")
    data class Join(val parent: BookClubs = BookClubs()) {
        @Resource("{id}")
        data class Id(val parent: Join = Join(), val id: Int)
    }
}