package com.indigo.woorm.goals.composables

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.button.pair.ButtonPair
import com.indigo.woorm.goals.GoalPreview
import com.indigo.woorm.goals.SetGoalHeader
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun GoalInput(
    goal: GoalPreview?,
    onConfirm: (Int) -> Unit,
    onCancel: () -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            104
        },
        initialPage = (goal?.amount ?: 12) + 2
    )
    val sheetBackgroundColor = AppTheme.colors.neutral.background
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                space = AppTheme.spacing.vertical.default
            )
        ) {
            SetGoalHeader()
            ButtonPair(
                onLeftClick = onCancel,
                leftLabel = "Cancel",
                leftContentDescription = "Cancel",
                onRightClick = { onConfirm((pagerState.currentPage - 2).coerceIn(0, 99)) },
                rightLabel = "Confirm",
                rightContentDescription = "Set goal",
            )
        }
        Spacer(
            modifier = Modifier.width(
                AppTheme.spacing.horizontal.default
            )
        )

        VerticalPager(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .heightIn(max = 192.dp)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(sheetBackgroundColor, Color.Transparent),
                            startY = 0f,
                            endY = size.height / 3f
                        )
                    )
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, sheetBackgroundColor),
                            startY = size.height * 2f / 3f,
                            endY = size.height
                        )
                    )
                },
            state = pagerState,
            pageSize = PageSize.Fixed(64.dp),
            snapPosition = SnapPosition.Center
        ) { page ->
            Text(
                text = "${page - 2}",
                style = MaterialTheme.typography.displayLarge,
                color = when {
                    page !in 2..101 -> Color.Transparent
                    pagerState.currentPage == page -> AppTheme.colors.neutral.primary
                    else -> AppTheme.colors.neutral.stroke
                }
            )

        }
    }
}
