package com.indigo.woorm.goals.composables.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.chip.Chip
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.goals.GoalPreview
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun GoalItem(
    goal: GoalPreview,
    onEdit: (GoalPreview) -> Unit,
    onDelete: (GoalPreview) -> Unit,
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.spacing.horizontal.screenMobile
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "${goal.year}",
                color = AppTheme.colors.neutral.text,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.vertical.textToText))
            Chip(
                label = "${goal.count}/${goal.amount} books read"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                variant = Variant.Ghost,
                style = Style.Neutral,
                onClick = { onEdit(goal) },
                icon = AppIcon.Pencil,
                contentDescription = "Edit goal"
            )
            IconButton(
                variant = Variant.Ghost,
                style = Style.Neutral,
                onClick = { onDelete(goal) },
                icon = AppIcon.Trash2,
                contentDescription = "Delete goal"
            )
        }
    }
}
