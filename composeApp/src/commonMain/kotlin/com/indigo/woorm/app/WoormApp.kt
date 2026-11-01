package com.indigo.woorm.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavUri
import androidx.navigation.compose.rememberNavController
import com.indigo.woorm.app.viewmodel.AppState
import com.indigo.woorm.app.viewmodel.AppViewModel
import com.indigo.woorm.auth.AuthScreen
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.navigation.ExternalUriHandler
import com.indigo.woorm.navigation.Navigation
import com.indigo.woorm.navigation.NavigationScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WoormApp(
    appViewModel: AppViewModel = koinViewModel()
) {
    val appState by appViewModel.appState.collectAsState()

    when (appState) {
        is AppState.Loading -> {
            Loading(
                modifier = Modifier.fillMaxSize()
            )
        }
        is AppState.Unauthenticated -> {
            AuthScreen()
        }
        is AppState.Authenticated -> {
            val navController = rememberNavController()

            // Deep links: platform entries deliver URIs here; the NavHost's
            // navDeepLink graph matches them to destinations. A URI matching
            // no destination (e.g. bare woorm.com) must NOT crash the app — it
            // opens to the start destination, same as the Android system path.
            DisposableEffect(Unit) {
                ExternalUriHandler.listener = { uri ->
                    try {
                        navController.navigate(NavUri(uri))
                    } catch (_: IllegalArgumentException) {
                        // No navDeepLink matches this URI; ignore (open-as-usual).
                    }
                }
                onDispose { ExternalUriHandler.listener = null }
            }

            NavigationScreen(navController) {
                Navigation(
                    navController = navController
                )
            }
        }
    }
}