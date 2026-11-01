package com.indigo.woorm.bookClubs.detail.membersBookStatusList.card.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.detail.BookClubDetail
import com.indigo.woorm.bookClubs.detail.MemberBookStatus
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Size

@Composable
fun Member(
    bookClubDetail: BookClubDetail,
    memberStatus: MemberBookStatus
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemberAvatar(
            bookClub = bookClubDetail,
            size = Size.Small
        )
        Spacer(
            modifier = Modifier.width(AppTheme.spacing.vertical.default)
        )
        Text(
            text = memberStatus.username,
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text,
        )
    }
}