package com.indigo.woorm.common.tab

import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.indigo.woorm.common.preview.ComponentPreview
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun Tabs(
    selectedTabIndex: Int,
    tabs: List<String>,
    onClick: (Int) -> Unit
) {
    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = AppTheme.colors.neutral.stroke,
        contentColor = AppTheme.colors.neutral.text,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                color = AppTheme.colors.neutral.primary
            )
        }
    ) {
        tabs.forEachIndexed { index, text ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onClick(index) },
                text = text
            )
        }
    }
}

@Preview
@Composable
private fun TabsPreview() {
    ComponentPreview {
        Tabs(
            selectedTabIndex = 0,
            tabs = listOf("Currently reading", "Previously read"),
            onClick = {}
        )
    }
}