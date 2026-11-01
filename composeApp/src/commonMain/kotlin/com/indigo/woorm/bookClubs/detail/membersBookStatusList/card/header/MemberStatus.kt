package com.indigo.woorm.bookClubs.detail.membersBookStatusList.card.header

import androidx.compose.runtime.Composable
import com.indigo.woorm.bookClubs.detail.MemberBookStatus
import com.indigo.woorm.common.chip.Chip
import com.indigo.woorm.library.service.dto.displayName
import com.indigo.woorm.library.service.dto.icon

@Composable
fun MemberStatus(memberStatus: MemberBookStatus) {
    val status = memberStatus.status
    Chip(
        label = status.displayName(),
        icon = status.icon()
    )
}