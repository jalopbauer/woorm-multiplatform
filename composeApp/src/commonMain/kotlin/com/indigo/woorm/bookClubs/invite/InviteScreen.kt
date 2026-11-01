package com.indigo.woorm.bookClubs.invite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun InviteScreen(
    viewModel: BookClubInviteViewModel,
    navigateToBookClubs: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collect {
            navigateToBookClubs()
        }
    }
    val invite by viewModel.bookClubInvite.collectAsState()
    Screen {
        invite?.let { invite ->
            Empty(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                id = Res.drawable.worm_book_club_invite,
                text = "Join your friends in ${invite.name}"
            ) {
                Button(
                    variant = Variant.Primary,
                    style = Style.Neutral,
                    shape = Rounded,
                    label = "Join",
                    onClick = {
                        viewModel.join(invite)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = "Join book club"
                )
            }
        }
    }
}