package com.indigo.woorm.books.detail.status

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.library.books.status.LibraryBookStatus
import com.indigo.woorm.ui.theme.Square
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant


@Composable
fun ReadBookAgain(
    status: LibraryBookStatus?,
    onClickReadAgain: () -> Unit,
    isLoading: Boolean
) {
    if (status is LibraryBookStatus.Read || status is LibraryBookStatus.Dnf) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            variant = Variant.Ghost,
            style = Style.Neutral,
            shape = Square,
            onClick = onClickReadAgain,
            label = "Read again",
            leadingIcon = AppIcon.RotateCcw,
            contentDescription = "Read book again",
            isLoading = isLoading
        )
    }
}