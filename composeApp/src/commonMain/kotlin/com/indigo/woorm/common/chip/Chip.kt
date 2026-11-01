package com.indigo.woorm.common.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    label: String,
    icon: AppIcon? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.radius.s))
            .background(AppTheme.colors.neutral.stroke)
            .padding(
                all = AppTheme.spacing.vertical.componentToComponent
            ),
        horizontalArrangement = horizontalArrangement
    ) {
        icon?.let { icon ->
            Icon(
                icon = icon,
                contentDescription = "",
                tint = AppTheme.colors.neutral.text
            )
            Spacer(
                modifier = Modifier.width(AppTheme.spacing.vertical.componentToComponent)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text,
            maxLines = 1
        )
    }
}