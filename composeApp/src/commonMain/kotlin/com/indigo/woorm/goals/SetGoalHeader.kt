package com.indigo.woorm.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun SetGoalHeader() {
    Column {
        FlowRow(
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon = AppIcon.Target,
                contentDescription = "Goal",
                size = Size.Medium,
                tint = AppTheme.colors.neutral.text
            )
            Spacer(
                modifier = Modifier.width(
                    width = AppTheme.spacing.vertical.textToText
                )
            )
            Text(
                text = stringResource(Res.string.goal_header_p1),
                color = AppTheme.colors.neutral.text,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(
                modifier = Modifier.width(
                    width = AppTheme.spacing.vertical.textToText
                )
            )
            Text(
                text = stringResource(Res.string.goal_header_p2),
                color = AppTheme.colors.neutral.text,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(
            modifier = Modifier.width(
                width = AppTheme.spacing.vertical.textToText
            )
        )
        Text(
            text = stringResource(Res.string.goal_header_p3),
            color = AppTheme.colors.neutral.text,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier.width(
                width = AppTheme.spacing.vertical.textToText
            )
        )
        Text(
            text = stringResource(Res.string.goal_help_text),
            color = AppTheme.colors.neutral.highlight,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
