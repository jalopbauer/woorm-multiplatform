package com.indigo.woorm.common.bottomSheet.confirm

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.bottomSheet.BottomSheet
import com.indigo.woorm.common.bottomSheet.BottomSheetState
import com.indigo.woorm.common.button.pair.ButtonPair
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style


@Composable
fun ConfirmationBottomSheet(
    state: BottomSheetState,
    onConfirm: () -> Unit,
    confirmEnabled: Boolean = true,
    confirmLabel: String = "Confirm",
    style: Style = Style.Neutral,
    header: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    BottomSheet(
        header = header,
        state = state
    ) {
        content()
        Spacer(
            modifier = Modifier.height(
                height = AppTheme.spacing.vertical.default
            )
        )
        ButtonPair(
            onLeftClick = {
                state.hide()
            },
            leftLabel = "Cancel",
            leftContentDescription = "Cancel",
            leftStyle = Style.Neutral,
            onRightClick = {
                onConfirm()
                state.hide()
            },
            rightLabel = confirmLabel,
            rightContentDescription = confirmLabel,
            rightStyle = style,
            confirmEnabled = confirmEnabled
        )
    }
}