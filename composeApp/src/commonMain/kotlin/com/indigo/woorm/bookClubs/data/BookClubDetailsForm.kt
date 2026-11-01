package com.indigo.woorm.bookClubs.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.data.bookClubColorSelect.BookClubColorSelect
import com.indigo.woorm.common.input.textField.TextField
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

@Composable
fun BookClubDetailsForm(
    bookClubData: BookClubData,
    onNameChange: (String) -> Unit,
    onClickColor: (BookClubColor) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookClubTextAvatar(
            bookClub = bookClubData,
            size = Size.Large
        )

        TextField(
            value = bookClubData.name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Name",
            maxLength = 50
        )
        BookClubColorSelect(
            bookClubData = bookClubData,
            onClickColor = onClickColor
        )
    }
}