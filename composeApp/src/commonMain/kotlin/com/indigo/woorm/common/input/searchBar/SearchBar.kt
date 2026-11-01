package com.indigo.woorm.common.input.searchBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.common.input.textInputColors
import com.indigo.woorm.common.preview.ComponentPreview
import com.indigo.woorm.ui.theme.AppTheme

private const val SEARCH_BAR_MAX_LENGTH = 200

@Composable
fun SearchBar(
    textFieldValue: TextFieldValue,
    autoFocus: Boolean = true,
    placeholderText: String,
    onValueChange: (TextFieldValue) -> Unit,
    onSubmit: (() -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (autoFocus) {
            focusRequester.requestFocus()
        }
    }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = {
            val truncated = if (it.text.length > SEARCH_BAR_MAX_LENGTH) {
                val text = it.text.take(SEARCH_BAR_MAX_LENGTH)
                it.copy(text = text, selection = TextRange(text.length.coerceAtMost(it.selection.end)))
            } else it
            onValueChange(truncated)
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        placeholder = {
            Text(
                text = placeholderText,
                color = AppTheme.colors.neutral.highlight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                icon = AppIcon.Search,
                contentDescription = "Search",
                tint = AppTheme.colors.neutral.text
            )
        },
        trailingIcon = {
            if (textFieldValue.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onValueChange(TextFieldValue(text = "", selection = TextRange.Zero))
                    }
                ) {
                    Icon(
                        icon = AppIcon.X,
                        contentDescription = "X",
                        tint = AppTheme.colors.neutral.text
                    )
                }
            }
        },
        shape = CircleShape,
        colors = textInputColors(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = if (onSubmit != null) ImeAction.Search else ImeAction.Default
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSubmit?.invoke() }
        )
    )
}

@Preview
@Composable
private fun SearchBarEmpty() {
    ComponentPreview {
        var textFieldValue: TextFieldValue by remember {
            mutableStateOf(
                TextFieldValue()
            )
        }
        SearchBar(
            textFieldValue = textFieldValue,
            placeholderText = "Search placeholder",
            onValueChange = { }
        )
    }
}

@Preview
@Composable
private fun SearchBarFilled() {
    ComponentPreview {
        var textFieldValue: TextFieldValue by remember {
            mutableStateOf(
                TextFieldValue("searching...")
            )
        }
        SearchBar(
            textFieldValue = textFieldValue,
            placeholderText = "Search placeholder",
            onValueChange = { }
        )
    }
}