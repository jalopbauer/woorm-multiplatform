package com.indigo.woorm.bookClubs.data

import kotlinx.serialization.Serializable

@Serializable
data class BookClubData(
    override val name: String,
    override val color: BookClubColor,
) : BookClub
