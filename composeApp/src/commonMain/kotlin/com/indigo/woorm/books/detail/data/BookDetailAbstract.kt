package com.indigo.woorm.books.detail.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.indigo.woorm.books.detail.BookDetail
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun BookDetailAbstract(book: BookDetail) {
    var collapsed by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.clickable(
            onClick = {
                collapsed = !collapsed
            }
        ),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = book.abstract.replace("\\n", "\n"),
            maxLines = if (collapsed) 4 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            color = AppTheme.colors.neutral.text
        )
        Button(
            modifier = Modifier.wrapContentSize(),
            variant = Variant.Ghost,
            style = Style.Neutral,
            shape = Rounded,
            label = "read more",
            contentDescription = "",
            onClick = {
                collapsed = !collapsed
            }
        )
    }
}