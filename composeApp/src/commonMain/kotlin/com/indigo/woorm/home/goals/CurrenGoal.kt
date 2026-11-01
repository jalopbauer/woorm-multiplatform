package com.indigo.woorm.home.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.common.preview.ComponentPreview
import com.indigo.woorm.goals.GoalPreview
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Circle
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.shape

@Composable
fun CurrentGoal(
    goal: GoalPreview?,
    onClick: () -> Unit
) {
    val goalSet = goal != null
    val goalShape = shape(if (goalSet) Circle else Rounded)
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = AppTheme.stroke.xs,
                color = AppTheme.colors.neutral.primary,
                shape = goalShape
            )
            .height(48.dp)
            .clip(goalShape)
            .background(if (goalSet) AppTheme.colors.neutral.background else AppTheme.colors.neutral.primary)
            .clickable(onClick=onClick),
    ) {
        if (!goalSet) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        horizontal = AppTheme.spacing.horizontal.default,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon = AppIcon.Target,
                    contentDescription = "Goal",
                    tint = AppTheme.colors.neutral.background
                )
                Spacer(
                    modifier = Modifier.padding(
                        horizontal = AppTheme.spacing.vertical.componentToComponent
                    )
                )
                Text(
                    text = "Set yearly goal",
                    color = AppTheme.colors.neutral.background
                )
            }
        } else {
            Spacer (
                modifier = Modifier
                    .fillMaxWidth(
                        goal.count / goal.amount.toFloat()
                    )
                    .fillMaxHeight()
                    .background(AppTheme.colors.neutral.stroke)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        horizontal = AppTheme.spacing.horizontal.default,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(
                        icon = AppIcon.Target,
                        contentDescription = "Goal",
                        tint = AppTheme.colors.neutral.text

                    )
                    Spacer(
                        modifier = Modifier.padding(
                            horizontal = AppTheme.spacing.vertical.componentToComponent
                        )
                    )
                    Text(
                        text = "Goal",
                        color = AppTheme.colors.neutral.text
                    )
                }
                Text(
                    text = "${goal.count}/${goal.amount}",
                    color = AppTheme.colors.neutral.text
                )
            }
        }
    }
}
@Preview
@Composable
private fun CurrentGoalPartiallyUnSet() {
    ComponentPreview {
        CurrentGoal(
            goal = null,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CurrentGoalPartiallyCompleted() {
    ComponentPreview {
        CurrentGoal(
            goal = GoalPreview(
                id = 1,
                amount = 10,
                year = 1969,
                count = 5
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CurrentGoalCompleted() {
    ComponentPreview {
        CurrentGoal(
            goal = GoalPreview(
                id = 1,
                amount = 10,
                year = 1969,
                count = 10
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CurrentGoalExceeded() {
    ComponentPreview {
        CurrentGoal(
            goal = GoalPreview(
                id = 1,
                amount = 10,
                year = 1969,
                count = 15
            ),
            onClick = {}
        )
    }
}