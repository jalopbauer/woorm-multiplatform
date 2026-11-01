package com.indigo.woorm.bookClubs.detail.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.library.service.dto.icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Circle
import com.indigo.woorm.ui.theme.shape

@Composable
fun BookClubStatusProgressIndicatorItem(
    status: LibraryBookStatusTypeDTO,
    count: Int,
) {
    val icon = status.icon()
    val people = when (count) {
        0 -> "No one has"
        1 -> "1 person has"
        else -> "$count people have"
    }

    val text = when (status) {
        LibraryBookStatusTypeDTO.IN_PROGRESS -> "$people started"
        LibraryBookStatusTypeDTO.READ -> "$people finished"
        LibraryBookStatusTypeDTO.DNF -> "$people abandoned"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = shape(Circle)
                )
                .background(
                    color = AppTheme.colors.neutral.stroke,
                )
                .padding(
                    all = AppTheme.spacing.vertical.componentToComponent
                )
        ) {
            Icon(
                icon = icon,
                contentDescription = text,
                tint = AppTheme.colors.neutral.text
            )
        }
        Spacer(
            modifier = Modifier.width(AppTheme.spacing.vertical.componentToComponent)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text
        )
    }
}