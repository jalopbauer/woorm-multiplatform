package com.indigo.woorm.books.detail.status

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.bottomSheet.BottomSheet
import com.indigo.woorm.common.bottomSheet.BottomSheetState
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.button.pair.ButtonPair
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun ReadPromptBottomSheet(
    state: BottomSheetState,
    title: String,
    text: String,
    closeContentDescription: String,
    noLabel: String,
    yesLabel: String,
    onNo: () -> Unit,
    onYes: () -> Unit,
) {
    BottomSheet(
        state = state,
        header = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    variant = Variant.Ghost,
                    style = Style.Neutral,
                    onClick = { state.hide() },
                    icon = AppIcon.X,
                    contentDescription = closeContentDescription
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppTheme.colors.neutral.text
                )
            }
        }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text
        )
        Spacer(
            modifier = Modifier.height(
                height = AppTheme.spacing.vertical.default
            )
        )
        ButtonPair(
            onLeftClick = {
                state.hide()
                onNo()
            },
            leftLabel = noLabel,
            leftContentDescription = noLabel,
            onRightClick = {
                state.hide()
                onYes()
            },
            rightLabel = yesLabel,
            rightContentDescription = yesLabel,
        )
    }
}
