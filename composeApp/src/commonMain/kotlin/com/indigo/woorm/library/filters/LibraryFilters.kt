package com.indigo.woorm.library.filters

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.select.single.SingleSelect

@Composable
fun LibraryFilters(
    currentSelectedFilter: LibraryBookStatusFilter?,
    onClickFilterBy: (LibraryBookStatusFilter?) -> Unit,
) {
    SingleSelect(
        items = LibraryBookStatusFilter.entries + listOf(null),
        selectedItem = currentSelectedFilter,
        onClickSelect = onClickFilterBy,
        label = {
            it?.displayName() ?: "All"
        },
        icon = { filter ->
            filter?.icon() ?: AppIcon.LibraryBig
        }
    )
}

@Preview
@Composable
private fun LibraryFiltersPrev() {
    MaterialTheme {
        LibraryFilters(
            currentSelectedFilter = LibraryBookStatusFilter.TBR,
            onClickFilterBy = {},
        )
    }
}
