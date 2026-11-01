package com.indigo.woorm.shelves.composables.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.button.icon.IconButton
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun ShelvesTopBar(
    onClickBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.neutral.background)
            .statusBarsPadding()
            .padding(all = AppTheme.padding.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            variant = Variant.Ghost,
            style = Style.Neutral,
            onClick = onClickBack,
            icon = AppIcon.ArrowLeft,
            contentDescription = "Back"
        )
        Text(
            text = "Shelves",
            color = AppTheme.colors.neutral.text,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
