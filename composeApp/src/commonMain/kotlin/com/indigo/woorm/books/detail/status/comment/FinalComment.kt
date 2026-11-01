package com.indigo.woorm.books.detail.status.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.button.pair.ButtonPair
import com.indigo.woorm.common.input.textBox.TextBox
import com.indigo.woorm.ui.theme.AppTheme

private const val FINAL_COMMENT_MAX_LENGTH = 1000

@Composable
fun FinalComment(
    comment: String?,
    onSaveComment: (String) -> Unit,
) {
    var draft by remember(comment) { mutableStateOf(comment.orEmpty()) }
    val isEditing = draft != comment.orEmpty()

    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.vertical.default)
    ) {
        TextBox(
            value = draft,
            onValueChange = {
                draft = it
            },
            modifier = Modifier.fillMaxWidth().heightIn(max = 250.dp),
            placeholder = "Final comment",
            maxLength = FINAL_COMMENT_MAX_LENGTH
        )
        if (isEditing) {
            ButtonPair(
                onLeftClick = {
                    draft = comment.orEmpty()
                },
                leftLabel = "Cancel",
                leftContentDescription = "Discard comment changes",
                onRightClick = {
                    onSaveComment(draft.trim())
                },
                rightLabel = "Save",
                rightContentDescription = "Save comment",
            )
        }
    }
}
