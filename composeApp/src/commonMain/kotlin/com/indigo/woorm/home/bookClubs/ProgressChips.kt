package com.indigo.woorm.home.bookClubs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.indigo.woorm.bookClubs.previews.BookClubPreview
import com.indigo.woorm.common.chip.Chip
import com.indigo.woorm.library.service.dto.icon
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun ProgressChips(bookClub: BookClubPreview) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.componentToComponent)
    ) {
        bookClub.readingProgress.toList().forEach { (status, count) ->
            Chip(
                label = "$count",
                icon = status.icon()
            )
        }
    }
}