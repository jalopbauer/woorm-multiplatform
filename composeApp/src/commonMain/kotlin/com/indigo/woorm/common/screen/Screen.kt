package com.indigo.woorm.common.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.indigo.woorm.ui.theme.AppTheme

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    gap: Dp = AppTheme.spacing.vertical.sectionToSection,
    horizontalPadding: Dp = AppTheme.spacing.horizontal.screenMobile,
    alignment: Alignment.Vertical = Alignment.Top,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        snackbarHost = snackbarHost,
        containerColor = AppTheme.colors.neutral.background,
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(horizontal = horizontalPadding),
            verticalArrangement = Arrangement.spacedBy(space = gap, alignment = alignment)
        ) {
            content()
        }
    }
}