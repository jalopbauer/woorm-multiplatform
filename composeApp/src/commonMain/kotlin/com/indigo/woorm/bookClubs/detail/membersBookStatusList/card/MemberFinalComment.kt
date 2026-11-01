package com.indigo.woorm.bookClubs.detail.membersBookStatusList.card

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.detail.MemberBookStatus
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun MemberFinalComment(
    memberStatus: MemberBookStatus,
    isCollapsed: Boolean
) {
    if (memberStatus.comment != null) {
        Spacer(
            modifier = Modifier.height(AppTheme.spacing.vertical.default)
        )
        Text(
            text = memberStatus.comment,
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text,
            maxLines = if (isCollapsed) 3 else Int.MAX_VALUE
        )
        Spacer(
            modifier = Modifier.height(AppTheme.spacing.vertical.componentToComponent)
        )
    }
}