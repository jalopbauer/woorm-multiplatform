package com.indigo.woorm.bookClubs.detail.progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.detail.BookClubDetail
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun BookClubStatusProgressIndicators(bookClubDetail: BookClubDetail) {
    Column {
        bookClubDetail.readingProgress.toList().forEachIndexed { index, (status, count) ->
            BookClubStatusProgressIndicatorItem(
                status = status,
                count = count,
            )
            if (index != bookClubDetail.readingProgress.size - 1) {
                Spacer(
                    modifier = Modifier.height(AppTheme.spacing.vertical.componentToComponent)
                )
            }
        }
    }
}