package com.indigo.woorm.common.select.single

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun <T> SingleSelect(
    items: List<T>,
    selectedItem: T,
    onClickSelect: (T) -> Unit,
    label: (T) -> String,
    icon: (T) -> AppIcon?,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.componentToComponent)
    ) {
        items(items) { item ->
            val selected = selectedItem == item
            SingleSelectItem(
                selected = selected,
                label = label(item),
                onClick = { onClickSelect(item) },
                leadingIcon = icon(item),
            )
        }
    }
}