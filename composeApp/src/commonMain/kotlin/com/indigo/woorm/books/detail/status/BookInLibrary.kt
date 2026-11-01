package com.indigo.woorm.books.detail.status

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.books.detail.viewModel.LibraryBookUiState
import com.indigo.woorm.books.detail.viewModel.action.ToLibrary
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.library.books.LibraryBook
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

@Composable
fun BookInLibrary(
    libraryBook: LibraryBook?,
    onClickBookInLibrary: () -> Unit,
    isLibraryBookEditing: LibraryBookUiState.Editing?,
) {
    val isInLibrary = libraryBook != null
    val isLoading = isLibraryBookEditing?.bookDetailAction is ToLibrary

    val contentDescription = when {
        isLoading && isInLibrary -> "Removing from library"
        isLoading -> "Adding to library"
        isInLibrary -> "Book in library"
        else -> "Add book to library"
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(48.dp)
            .then(
                if (!isLoading) Modifier.clickable(onClick = onClickBookInLibrary)
                else Modifier
            )
            .background(
                color = AppTheme.colors.neutral.background,
                shape = RoundedCornerShape(AppTheme.radius.s)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = AppTheme.colors.neutral.primary,
                strokeWidth = 4.dp,
            )

        } else {
            val leadingIcon = if (isInLibrary) AppIcon.HeartFilled else AppIcon.Heart
            Icon(
                icon = leadingIcon,
                contentDescription = contentDescription,
                tint = AppTheme.colors.neutral.primary,
                size = Size.Medium
            )
        }
        Spacer(Modifier.size(AppTheme.spacing.vertical.componentToComponent))
        Text(
            text = contentDescription,
            style = MaterialTheme.typography.titleLarge,
            color = AppTheme.colors.neutral.text,
        )
    }
}