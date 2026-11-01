package com.indigo.woorm.goals

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.bottomSheet.BottomSheet
import com.indigo.woorm.common.bottomSheet.confirm.ConfirmationBottomSheet
import com.indigo.woorm.common.bottomSheet.rememberBottomSheetState
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.button.fab.FloatingActionButton
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.preview.ScreenPreview
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.goals.composables.GoalInput
import com.indigo.woorm.goals.composables.list.GoalList
import com.indigo.woorm.goals.composables.topBar.GoalsTopBar
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import kotlin.time.Clock
import kotlinx.datetime.todayIn
import kotlinx.datetime.TimeZone

@Composable
fun GoalsContent(
    goals: List<GoalPreview>,
    onClickBack: () -> Unit,
    onCreateGoal: (Int) -> Unit,
    onSetGoal: (GoalPreview, Int) -> Unit,
    onDeleteGoal: (GoalPreview) -> Unit,
) {
    val goalSheetState = rememberBottomSheetState(
        skipPartiallyExpanded = true
    )
    var editingGoal by remember { mutableStateOf<GoalPreview?>(null) }
    val deleteSheetState = rememberBottomSheetState()
    var deletingGoal by remember { mutableStateOf<GoalPreview?>(null) }
    val currentYear = Clock.System.todayIn(TimeZone.currentSystemDefault()).year

    Screen(
        topBar = { GoalsTopBar(onClickBack = onClickBack) },
        horizontalPadding = if (goals.isNotEmpty()) AppTheme.spacing.horizontal.none else AppTheme.spacing.horizontal.screenMobile,
        floatingActionButton = {
            if (goals.isNotEmpty() && goals.none { it.year == currentYear }) {
                FloatingActionButton(
                    onFabClick = {
                        editingGoal = null
                        goalSheetState.show()
                    },
                    icon = AppIcon.Plus,
                    contentDescription = "Create Goal"
                )
            }
        }
    ) {
        if (goals.isEmpty()) {
            Empty(
                modifier = Modifier.fillMaxSize(),
                id = Res.drawable.empty_goals,
                text = "Set a yearly reading goal to track your progress"
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    variant = Variant.Primary,
                    style = Style.Neutral,
                    leadingIcon = AppIcon.Plus,
                    shape = Rounded,
                    contentDescription = "Create goal",
                    label = "Create goal",
                    onClick = {
                        editingGoal = null
                        goalSheetState.show()
                    }
                )
            }
        } else {
            GoalList(
                goals = goals,
                onEdit = {
                    editingGoal = it
                    goalSheetState.show()
                },
                onDelete = {
                    deletingGoal = it
                    deleteSheetState.show()
                }
            )
        }
    }

    BottomSheet(
        state = goalSheetState,
    ) {
        GoalInput(
            goal = editingGoal,
            onConfirm = { amount ->
                val goal = editingGoal
                if (goal != null) {
                    onSetGoal(goal, amount)
                } else {
                    onCreateGoal(amount)
                }
                editingGoal = null
                goalSheetState.hide()
            },
            onCancel = {
                goalSheetState.hide()
            }
        )
    }

    ConfirmationBottomSheet(
        state = deleteSheetState,
        onConfirm = {
            deletingGoal?.let(onDeleteGoal)
        },
        confirmLabel = "Delete goal",
        style = Style.Danger,
        header = {
            Text(
                text = "Delete goal",
                style = MaterialTheme.typography.headlineSmall,
                color = AppTheme.colors.neutral.text
            )
        }
    ) {
        Text(
            text = "Your reading goal for ${deletingGoal?.year} will be deleted. This action cannot be undone.",
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text
        )
    }
}

@Preview
@Composable
private fun GoalsContent_Preview() {
    ScreenPreview {
        GoalsContent(
            goals = listOf(
                GoalPreview(id = 1, amount = 24, year = 2026, count = 12),
                GoalPreview(id = 2, amount = 12, year = 2025, count = 12)
            ),
            onClickBack = {},
            onCreateGoal = {},
            onSetGoal = { _, _ -> },
            onDeleteGoal = {},
        )
    }
}
