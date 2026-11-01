package com.indigo.woorm.books.detail.status.stars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

internal val StarSize = 48.dp

@Composable
fun Star(index: Int, currentRating: Double, rate: (Double) -> Unit) {
    val leftIcon = if (currentRating >= index - 0.5) {
        AppIcon.StarLeftFilled
    } else {
        AppIcon.StarLeftEmpty
    }
    val rightIcon = if (currentRating >= index.toDouble()) {
        AppIcon.StarRightFilled
    } else {
        AppIcon.StarRightEmpty
    }
    Row(
        modifier = Modifier
            .height(StarSize)
            .width(StarSize*2),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    rate(index - 0.5)
                }
        ) {
            Icon(
                icon = leftIcon,
                contentDescription = "Star",
                tint = AppTheme.colors.star,
                size = Size.Large
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    rate(index.toDouble())
                }

        ) {
            Icon(
                icon = rightIcon,
                contentDescription = "Star",
                tint = AppTheme.colors.star,
                size = Size.Large
            )
        }
    }
}
