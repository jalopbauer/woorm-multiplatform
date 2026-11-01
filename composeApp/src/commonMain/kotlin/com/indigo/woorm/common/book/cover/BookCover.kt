package com.indigo.woorm.common.book.cover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.book.Book
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.util.inline.runIf

@Composable
fun <T : Book> BookCover(
    modifier: Modifier = Modifier,
    book: T,
    radius: Boolean = true,
    onCoverClick: ((T) -> Unit)? = null
) {
    val url = book.coverUrl
    val title = book.title
    val authors = book.authors()
    var errorOrPlaceholder by remember { mutableStateOf(false) }
    Box (
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(
            modifier = modifier
                .aspectRatio(174f / 263f)
                .runIf(radius) {
                    clip(RoundedCornerShape(AppTheme.radius.s))
                }
                .run {
                    if (onCoverClick != null) clickable(onClick = { onCoverClick(book) })
                    else this
                },
            alignment = Alignment.Center,
            model = url,
            contentDescription = "$title by $authors",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.cover_placeholder),
            error = painterResource(Res.drawable.cover_placeholder),
            onError = { errorOrPlaceholder = true },
            onSuccess = { errorOrPlaceholder = false },
        )
        if (errorOrPlaceholder) {
            Text(
                text = title,
                textAlign = TextAlign.Center
            )
        }
    }
}