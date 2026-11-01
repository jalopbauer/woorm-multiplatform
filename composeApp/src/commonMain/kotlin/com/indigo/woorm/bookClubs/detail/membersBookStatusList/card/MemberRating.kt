package com.indigo.woorm.bookClubs.detail.membersBookStatusList.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.bookClubs.detail.MemberBookStatus
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun MemberRating(memberStatus: MemberBookStatus) {
    if (memberStatus.rating != null) {
        Row(
            modifier = Modifier
                .padding(
                    all = AppTheme.spacing.vertical.componentToComponent
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${memberStatus.rating}",
                style = MaterialTheme.typography.bodyLarge,
                color = AppTheme.colors.neutral.text
            )
            Spacer(
                modifier = Modifier.width(AppTheme.spacing.vertical.textToText)
            )
            Icon(
                icon = AppIcon.StarFilled,
                contentDescription = "Star",
                tint = AppTheme.colors.star
            )
        }
    }
}