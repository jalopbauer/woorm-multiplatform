package com.indigo.woorm.library.books.shelves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme


@Composable
@OptIn(ExperimentalLayoutApi::class)
fun <T: Shelf> ShelvesChipFlow(
    shelves: List<T>,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 48.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon = AppIcon.Shelf,
                    contentDescription = "Shelves",
                    tint = AppTheme.colors.neutral.text,
                )
                Spacer(
                    modifier = Modifier.width(AppTheme.spacing.vertical.textToText)
                )
                Text(
                    "Shelves",
                    color = AppTheme.colors.neutral.text,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            if (shelves.isEmpty())  {
                Icon(
                    icon = AppIcon.Plus,
                    contentDescription = "Show all shelves"
                )
            } else {
                Icon(
                    icon = AppIcon.ChevronDown,
                    contentDescription = "Show all shelves"
                )
            }
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.horizontal.componentToComponent),
        ) {
            repeat(shelves.size) { x ->
                ShelfChip(shelves[x])
            }
        }
    }
}