package com.indigo.woorm.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.card.Card
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.ui.theme.Min
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    navigateToShelves: () -> Unit,
    navigateToGoals: () -> Unit
) {
    Screen(
        alignment = Alignment.Bottom
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            variant = Variant.Primary,
            style= Style.Neutral,
            shape = Rounded,
            leadingIcon = AppIcon.Target,
            label = "Edit goals",
            contentDescription= "Navigate to goals",
            onClick = navigateToGoals
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            variant = Variant.Primary,
            style= Style.Neutral,
            shape = Rounded,
            leadingIcon = AppIcon.Shelf,
            label = "Edit shelves",
            contentDescription= "Navigate to shelves",
            onClick = navigateToShelves
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            variant = Variant.Ghost,
            style = Style.Danger,
            shape = Rounded,
            label = "Logout",
            contentDescription = ""
        ) {
            viewModel.logout()
        }
    }
}