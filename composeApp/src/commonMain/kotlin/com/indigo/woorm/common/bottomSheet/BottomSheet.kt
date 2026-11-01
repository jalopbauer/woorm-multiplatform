package com.indigo.woorm.common.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.indigo.woorm.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class BottomSheetState(
    val sheetState: SheetState,
    private val coroutineScope: CoroutineScope
) {
    var isVisible by mutableStateOf(false)
        private set

    fun show() {
        isVisible = true
    }

    fun hide() {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                isVisible = false
            }
        }
    }

    internal fun onDismiss() {
        isVisible = false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberBottomSheetState(
    skipPartiallyExpanded: Boolean = false,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): BottomSheetState {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    return remember(sheetState, coroutineScope) {
        BottomSheetState(sheetState, coroutineScope)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    state: BottomSheetState,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    if (state.isVisible) {
        ModalBottomSheet(
            onDismissRequest = { state.onDismiss() },
            sheetState = state.sheetState,
            containerColor = AppTheme.colors.neutral.background,
            dragHandle = {}
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppTheme.spacing.horizontal.screenMobile,
                    vertical = AppTheme.spacing.vertical.sectionToSection,
                )
            ) {
                header()
                Spacer(
                    modifier = Modifier.height(
                        height = AppTheme.spacing.vertical.default
                    )
                )
                content()
            }
        }
    }
}
