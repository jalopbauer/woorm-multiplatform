package com.indigo.woorm.home.goals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.goals.GoalPreview
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun HomeTopBar(goal: GoalPreview?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = AppTheme.spacing.horizontal.screenMobile)
    ) {
        CurrentGoal(
            goal = goal,
            onClick = onClick
        )
    }
}