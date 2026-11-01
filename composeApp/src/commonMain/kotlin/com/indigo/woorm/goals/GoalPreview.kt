package com.indigo.woorm.goals

import kotlinx.serialization.Serializable

@Serializable
data class GoalPreview(
    val id: Int,
    val amount: Int,
    val year: Int,
    val count: Long,
)