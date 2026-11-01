package com.indigo.woorm.bookClubs.data.bookClubColorSelect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.data.BookClubColor
import com.indigo.woorm.bookClubs.data.BookClubData
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookClubColorSelect(
    bookClubData: BookClubData,
    onClickColor: (BookClubColor) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            AppTheme.spacing.vertical.default,
            Alignment.CenterHorizontally
        ),
    ) {
        BookClubColor.entries.forEach {
            BookClubColorSelectItem(
                expected = it,
                current = bookClubData.color,
                onClickColor = onClickColor
            )
        }
    }
}