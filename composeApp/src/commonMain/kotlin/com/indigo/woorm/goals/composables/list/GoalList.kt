package com.indigo.woorm.goals.composables.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.indigo.woorm.goals.GoalPreview
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun GoalList(
    goals: List<GoalPreview>,
    onEdit: (GoalPreview) -> Unit,
    onDelete: (GoalPreview) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.horizontal.default)
    ) {
        items(goals) { goal ->
            GoalItem(
                goal = goal,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}
