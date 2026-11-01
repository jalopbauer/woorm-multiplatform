package com.indigo.woorm.common.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Shape
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun Button(
    modifier: Modifier = Modifier,
    variant: Variant,
    style: Style,
    shape: Shape,
    label: String,
    contentDescription: String,
    leadingIcon: AppIcon? = null,
    trailingIcon: AppIcon? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {

    val buttonStyle = ButtonStyle(
        variant = variant,
        style = style,
        shape = shape
    )

    val text = buttonStyle.text()

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        isLoading = isLoading,
        buttonStyle = buttonStyle
    ) {
        leadingIcon?.let {
            Icon(
                icon = it,
                contentDescription = contentDescription,
                tint = text,
            )
            Spacer(Modifier.width(AppTheme.spacing.horizontal.componentToComponent))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = text,
        )
        trailingIcon?.let {
            Spacer(Modifier.width(AppTheme.spacing.horizontal.componentToComponent))
            Icon(
                icon = it,
                contentDescription = contentDescription,
                tint = text,
            )
        }
    }
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    variant: Variant,
    style: Style,
    shape: Shape,
    icon: AppIcon,
    contentDescription: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val buttonStyle = ButtonStyle(
        variant = variant,
        style = style,
        shape = shape
    )

    val text = buttonStyle.text()

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        buttonStyle = buttonStyle
    ) {
        Icon(
            icon = icon,
            contentDescription = contentDescription,
            tint = text,
        )
    }
}

@Composable
private fun Button(
    modifier: Modifier,
    buttonStyle: ButtonStyle,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {

    val composeShape = buttonStyle.composeShape()
    val domain = buttonStyle.domain()
    val background = buttonStyle.background()
    val border = buttonStyle.border()

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = domain.text,
        ),
        shape = composeShape,
        border = border,
        contentPadding = PaddingValues(
            horizontal = AppTheme.spacing.horizontal.default,
            vertical = 12.dp
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = buttonStyle.text(),
            )
        } else {
            content()
        }
    }
}