package com.indigo.woorm.common.empty

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.indigo.woorm.ui.theme.AppTheme


@Composable
fun Empty(
    modifier: Modifier = Modifier,
    id: DrawableResource,
    text: String,
    callToAction:( @Composable ColumnScope.() -> Unit)?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id),
            contentDescription = null
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            color = AppTheme.colors.neutral.text
        )
        if (callToAction != null) {
            Spacer(
                modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
            )
            callToAction()
        }
    }
}