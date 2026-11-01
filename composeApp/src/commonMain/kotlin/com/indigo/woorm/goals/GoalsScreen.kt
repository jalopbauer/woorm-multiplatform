package com.indigo.woorm.goals

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.goals.viewModel.GoalsState
import com.indigo.woorm.goals.viewModel.GoalsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GoalsScreen(
    onClickBack: () -> Unit,
    viewModel: GoalsViewModel = koinViewModel()
) {
    val goalsState by viewModel.goalsState.collectAsState()

    when (val state = goalsState) {
        is GoalsState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is GoalsState.Success -> GoalsContent(
            goals = state.goals,
            onClickBack = onClickBack,
            onCreateGoal = viewModel::createGoal,
            onSetGoal = viewModel::setGoal,
            onDeleteGoal = viewModel::deleteGoal
        )
    }
}
