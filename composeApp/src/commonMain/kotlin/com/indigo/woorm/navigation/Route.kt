package com.indigo.woorm.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    sealed interface Base : Route {
        @Serializable
        data object Home : Base

        @Serializable
        class Library : Base

        @Serializable
        data object Clubs : Base

        @Serializable
        class Search : Base

        @Serializable
        data object Woorm : Base
    }

    @Serializable
    data class BookDetail(val id: Int) : Route

    @Serializable
    data object Shelves : Route

    @Serializable
    data object Goals : Route

    @Serializable
    data class BookClub(val id: String) : Route {
        @Serializable
        data class Invite(
            val id: Int
        )
    }
}