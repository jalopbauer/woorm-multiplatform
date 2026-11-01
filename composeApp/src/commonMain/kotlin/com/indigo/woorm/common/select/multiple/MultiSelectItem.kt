package com.indigo.woorm.common.select.multiple

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Circle
import com.indigo.woorm.ui.theme.shape

@Composable
fun MultiSelectItem(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    leadingIcon: AppIcon? = null,
) {
    val text = if (selected) AppTheme.colors.neutral.background else AppTheme.colors.neutral.text
    val border = if (selected) null else BorderStroke(width = AppTheme.stroke.s, color = AppTheme.colors.neutral.primary)

    FilterChip(
        modifier = Modifier.height(48.dp),
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = text,
            )
        },
        selected = selected,
        leadingIcon = leadingIcon?.let {{
            Icon(
                icon = leadingIcon,
                contentDescription = label,
                tint = text,
            )
        }},
        colors = FilterChipDefaults.filterChipColors(
            containerColor = AppTheme.colors.neutral.background,
            selectedContainerColor = AppTheme.colors.neutral.primary
        ),
        border = border,
        shape = shape(Circle)
    )
}

@Preview
@Composable
private fun MultiSelectItemPreviewSelectedTrue() {
    MaterialTheme {
        MultiSelectItem(
            selected = true,
            onClick = {},
            label = "Item name",
            leadingIcon = AppIcon.Square,
        )
    }
}

@Preview
@Composable
private fun MultiSelectItemPreviewSelectedFalse() {
    MaterialTheme {
        MultiSelectItem(
            selected = false,
            onClick = {},
            label = "Item name",
            leadingIcon = AppIcon.Square
        )
    }
}