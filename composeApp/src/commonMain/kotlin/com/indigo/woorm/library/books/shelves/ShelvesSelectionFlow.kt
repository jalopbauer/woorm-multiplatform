package com.indigo.woorm.library.books.shelves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant


@Composable
@OptIn(ExperimentalLayoutApi::class)
fun <T: Shelf> ShelvesSelectionFlow(
    shelves: List<T>,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    shelf:  @Composable (T) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.horizontal.componentToComponent),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.componentToComponent),
    ) {
        repeat(shelves.size) { x ->
            shelf(shelves[x])
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 4.dp,
                color = AppTheme.colors.neutral.primary
            )
        } else {
            IconButton(
                variant = Variant.Ghost,
                style = Style.Neutral,
                onClick = onClick,
                icon = AppIcon.Plus,
                contentDescription = "Add shelf",
            )
        }
    }
}