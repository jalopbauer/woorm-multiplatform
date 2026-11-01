package com.indigo.woorm.books.detail.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indigo.woorm.books.detail.viewModel.LibraryBookUiState
import com.indigo.woorm.books.detail.viewModel.action.BookInProgress
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.preview.ComponentPreview
import com.indigo.woorm.library.books.status.LibraryBookStatus
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Square
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import kotlin.time.Clock
import kotlinx.datetime.todayIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun <T : LibraryBookStatus?> LibraryBookStatusSelect(
    status: T,
    icon: AppIcon,
    label: String,
    selectedStatus: (T) -> Boolean,
    onClick: () -> Unit,
    isLoading: Boolean
) {
    val variant = if(selectedStatus(status)) Variant.Primary else Variant.Ghost
    if (isLoading) {
        Box(
            modifier = Modifier.height(48.dp).fillMaxWidth().background(AppTheme.colors.neutral.primary),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = AppTheme.colors.neutral.background
            )
        }
    } else {
        Button(
            modifier = Modifier.fillMaxWidth(),
            variant = variant,
            shape = Square,
            style = Style.Neutral,
            onClick = onClick,
            label = label,
            leadingIcon = icon,
            contentDescription = "Start reading book"
        )
    }
}

@Preview
@Composable
private fun LibraryBookStatusSelect_MatchesExpectedPreview() {
    ComponentPreview {
        LibraryBookStatusSelect(
            status = LibraryBookStatus.InProgress(
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                startDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            ),
            icon = AppIcon.BookOpen,
            label = "In progress",
            selectedStatus = { status -> status is LibraryBookStatus.InProgress },
            onClick = {},
            isLoading = false,
        )
    }
}

@Preview
@Composable
private fun LibraryBookStatusSelect_DoesNotMatchesExpectedStatusPreview() {
    ComponentPreview {
        LibraryBookStatusSelect(
            status = LibraryBookStatus.Dnf(
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                startDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            ),
            icon = AppIcon.BookOpen,
            label = "In progress",
            selectedStatus = { status -> status is LibraryBookStatus.InProgress },
            onClick = {},
            isLoading = false,
        )
    }
}

@Preview
@Composable
private fun LibraryBookStatusSelectNull_DoesNotMatchesExpectedStatusPreview() {
    ComponentPreview {
        LibraryBookStatusSelect(
            status = null,
            icon = AppIcon.BookOpen,
            label = "In progress",
            selectedStatus = { status -> status is LibraryBookStatus.InProgress },
            onClick = {},
            isLoading = false,
        )
    }
}