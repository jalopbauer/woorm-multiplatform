package com.indigo.woorm.goals.service

import kotlinx.serialization.Serializable

@Serializable
data class UpdateGoal(
    val amount: Int,
)