package com.indigo.woorm.shelves

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.bottomSheet.BottomSheetState
import com.indigo.woorm.common.bottomSheet.confirm.ConfirmationBottomSheet
import com.indigo.woorm.common.input.textField.TextField
import com.indigo.woorm.ui.theme.AppTheme


@Composable
fun CreateShelfBottomSheet(
    onConfirm: () -> Unit,
    newShelfName: String,
    createShelfSheetState: BottomSheetState,
    onValueChange: (String) -> Unit
) {
    ConfirmationBottomSheet(
        header = {
            Text(
                text = "Create shelf",
                color = AppTheme.colors.neutral.text,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        onConfirm = onConfirm,
        confirmEnabled = newShelfName.trim().isNotBlank(),
        state = createShelfSheetState,
    ) {
        TextField(
            value = newShelfName,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Name",
            maxLength = 50
        )
    }
}