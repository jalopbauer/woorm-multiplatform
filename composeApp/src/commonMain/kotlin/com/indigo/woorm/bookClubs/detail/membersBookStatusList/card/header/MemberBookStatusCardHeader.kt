package com.indigo.woorm.bookClubs.detail.membersBookStatusList.card.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.detail.BookClubDetail
import com.indigo.woorm.bookClubs.detail.MemberBookStatus

@Composable
fun MemberBookStatusCardHeader(
    bookClubDetail: BookClubDetail,
    memberStatus: MemberBookStatus
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Member(
            bookClubDetail = bookClubDetail,
            memberStatus = memberStatus
        )
        MemberStatus(
            memberStatus = memberStatus
        )
    }
}