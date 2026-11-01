package com.indigo.woorm.library

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.library.filters.LibraryBookStatusFilter
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun EmptyLibraryContent(
    currentSelectedFilter: LibraryBookStatusFilter?,
    onClickNavigateToSearch: () -> Unit,
    onClickNavigateToInProgress: () -> Unit,
    onClickNavigateToTbr: () -> Unit
) {
    val currentFilterEmptyString = when (currentSelectedFilter) {
        LibraryBookStatusFilter.TBR -> "in your TBR"
        LibraryBookStatusFilter.READ -> "finished"
        LibraryBookStatusFilter.IN_PROGRESS -> "in progress"
        LibraryBookStatusFilter.DNF -> "abandoned"
        null -> "in your library"
    }

    Empty(
        modifier = Modifier.fillMaxSize(),
        id = Res.drawable.worm_library_empty,
        text = "No books $currentFilterEmptyString"
    ) {
        when (currentSelectedFilter) {
            LibraryBookStatusFilter.TBR, null -> {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    variant = Variant.Primary,
                    style = Style.Neutral,
                    shape = Rounded,
                    label = "Start searching",
                    leadingIcon = AppIcon.Search,
                    contentDescription = "Start searching",
                    onClick = onClickNavigateToSearch
                )
            }

            LibraryBookStatusFilter.READ -> {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    variant = Variant.Primary,
                    style = Style.Neutral,
                    shape = Rounded,
                    label = "Finish one of your books",
                    leadingIcon = AppIcon.BookOpen,
                    contentDescription = "Finish one of your books",
                    onClick = onClickNavigateToInProgress
                )
            }

            LibraryBookStatusFilter.IN_PROGRESS -> {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    variant = Variant.Primary,
                    style = Style.Neutral,
                    shape = Rounded,
                    label = "Start your next great read",
                    leadingIcon = AppIcon.Heart,
                    contentDescription = "Start your next great read",
                    onClick = onClickNavigateToTbr
                )
            }

            LibraryBookStatusFilter.DNF -> {}
        }
    }
}
