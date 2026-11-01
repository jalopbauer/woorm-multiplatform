package com.indigo.woorm.library.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.library.service.dto.LibraryBookStatusTypeDTO
import com.indigo.woorm.library.service.dto.icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Circle
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant


@Composable
fun LibraryFilterCollapsed(
    currentSelectedStatus: LibraryBookStatusTypeDTO?,
    onClickFilterBy: (LibraryBookStatusTypeDTO?) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.componentToComponent)
    ) {
        Text(
            text = "Status",
            style = MaterialTheme.typography.titleLarge,
            color = AppTheme.colors.neutral.text
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.textToText)
        ) {
            LibraryBookStatusTypeDTO.entries.forEach { status ->
                val selected = currentSelectedStatus == status
                val variant = if (selected) Variant.Primary else Variant.Outlined
                val icon = status.icon()
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f),
                    onClick = {
                        if (selected) onClickFilterBy(null) else onClickFilterBy(status)
                    },
                    icon = icon,
                    variant = variant,
                    style = Style.Neutral,
                    shape = Circle,
                    contentDescription = status.let { "Filter by $status" }
                )
            }
        }
    }
}