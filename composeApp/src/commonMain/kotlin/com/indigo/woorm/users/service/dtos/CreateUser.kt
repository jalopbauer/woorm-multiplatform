package com.indigo.woorm.users.service.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateUser(
    val name: String,
)