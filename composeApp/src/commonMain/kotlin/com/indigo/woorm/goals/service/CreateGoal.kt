package com.indigo.woorm.goals.service

import kotlinx.serialization.Serializable

@Serializable
data class CreateGoal(
    val year: Int,
    val amount: Int,
)