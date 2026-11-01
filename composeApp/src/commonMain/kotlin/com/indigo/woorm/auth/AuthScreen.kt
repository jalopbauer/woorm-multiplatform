package com.indigo.woorm.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.auth.AuthUiState
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.common.input.textField.TextField
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel()
) {
    Screen(
        alignment = Alignment.Bottom
    ) {
        var username by remember { mutableStateOf("") }
        var id by remember { mutableStateOf("") }
        var showId by remember { mutableStateOf(false) }
        val state by viewModel.state.collectAsState()


        Empty(
            modifier = Modifier.fillMaxSize().weight(1f),
            id = Res.drawable.worm_book_not_selected,
            text = "Woorm helps you organize your reading experiences",
            callToAction = null
        )

        when (val current = state) {
            is AuthUiState.Loading -> Text(
                "Waking up the server…",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = AppTheme.colors.neutral.text
            )
            is AuthUiState.Error -> Text(
                current.message,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.vertical.componentToComponent))

        if (!showId) {
            TextField(
                value = username,
                onValueChange = {
                    username = it
                    if (state is AuthUiState.Error) viewModel.clearError()
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Username",
                maxLength = 50
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                variant = Variant.Primary,
                style = Style.Neutral,
                shape = Rounded,
                label = "Register",
                contentDescription = "",
                enabled = username.isNotBlank() && state !is AuthUiState.Loading,
                onClick = {
                    viewModel.register(username.trim())
                }
            )
        }

        if (showId) {
            TextField(
                value = id,
                onValueChange = {
                    id = it
                    if (state is AuthUiState.Error) viewModel.clearError()
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Id"
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            variant = if (showId) Variant.Primary else Variant.Ghost,
            style = Style.Neutral ,
            shape = Rounded,
            label = "Login",
            contentDescription = "",
            enabled = (!showId || id.isNotBlank()) && state !is AuthUiState.Loading,
            onClick = {
                if (showId) {
                    viewModel.login(id.trim())
                } else {
                    showId = true
                }
            }
        )
        Spacer(
            modifier = Modifier.height(AppTheme.spacing.vertical.sectionToSection)
        )
    }
}