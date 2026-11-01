package com.indigo.woorm.common.button.pair

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun ButtonPair(
    onLeftClick: () -> Unit,
    leftLabel: String,
    leftIcon: AppIcon? = null,
    leftContentDescription: String,
    leftStyle: Style = Style.Neutral,
    onRightClick: () -> Unit,
    rightLabel: String,
    rightIcon: AppIcon? = null,
    rightContentDescription: String,
    confirmEnabled: Boolean = true,
    rightStyle: Style = Style.Neutral,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = AppTheme.padding.xs,
            alignment = Alignment.End
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            variant = Variant.Ghost,
            style = leftStyle,
            onClick = onLeftClick,
            label = leftLabel,
            leadingIcon = leftIcon,
            contentDescription = leftContentDescription,
            shape = Rounded
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            variant = Variant.Primary,
            style = rightStyle,
            onClick = onRightClick,
            label = rightLabel,
            leadingIcon = rightIcon,
            contentDescription = rightContentDescription,
            shape = Rounded,
            enabled = confirmEnabled,
        )
    }
}