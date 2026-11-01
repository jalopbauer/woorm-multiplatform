package com.indigo.woorm.bookClubs.detail.membersBookStatusList.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.detail.BookClubDetail
import com.indigo.woorm.bookClubs.detail.MemberBookStatus
import com.indigo.woorm.bookClubs.detail.membersBookStatusList.card.header.MemberBookStatusCardHeader
import com.indigo.woorm.common.card.Card
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun MemberBookStatusCard(
    bookClubDetail: BookClubDetail,
    memberStatus: MemberBookStatus
) {
    var isCollapsed by remember { mutableStateOf(true) }
    Card (
        onClick = {
            isCollapsed = !isCollapsed
        }
    ) {
        Column(
            modifier = Modifier
                .padding(AppTheme.spacing.vertical.componentToComponent)
                .fillMaxWidth(),
        ) {
            MemberBookStatusCardHeader(
                bookClubDetail = bookClubDetail,
                memberStatus = memberStatus
            )
            MemberFinalComment(
                memberStatus = memberStatus,
                isCollapsed = isCollapsed
            )
            MemberRating(
                memberStatus = memberStatus
            )
        }
    }
}