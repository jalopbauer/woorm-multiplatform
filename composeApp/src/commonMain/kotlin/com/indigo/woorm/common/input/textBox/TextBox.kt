package com.indigo.woorm.common.input.textBox

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.indigo.woorm.common.input.filterAscii
import com.indigo.woorm.common.input.textInputColors
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.shape

@Composable
fun TextBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int? = null
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val filtered = filterAscii(it, allowNewlineAndTab = true)
            val truncated = if (maxLength != null) filtered.take(maxLength) else filtered
            onValueChange(truncated)
        },
        placeholder = {
            Text(
                text = placeholder,
                color = AppTheme.colors.neutral.stroke
            )
        },
        supportingText = if (maxLength != null && value.length >= maxLength * 0.8) {
            {
                Text(
                    text = "${value.length}/$maxLength",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = AppTheme.colors.neutral.text
                )
            }
        } else null,
        keyboardOptions = keyboardOptions,
        colors = textInputColors(),
        shape = shape(Rounded),
    )
}
