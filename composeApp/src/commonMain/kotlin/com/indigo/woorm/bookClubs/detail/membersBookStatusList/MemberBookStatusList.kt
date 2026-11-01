package com.indigo.woorm.bookClubs.detail.membersBookStatusList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.detail.BookClubDetail
import com.indigo.woorm.bookClubs.detail.MemberBookStatus
import com.indigo.woorm.bookClubs.detail.membersBookStatusList.card.MemberBookStatusCard
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun MemberBookStatusList(
    bookClubDetail: BookClubDetail,
    memberBookStatuses: List<MemberBookStatus>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        memberBookStatuses.forEach { memberStatus ->
            MemberBookStatusCard(
                bookClubDetail = bookClubDetail,
                memberStatus = memberStatus
            )
            Spacer(
                modifier = Modifier.height(AppTheme.spacing.vertical.componentToComponent)
            )
        }
    }
}