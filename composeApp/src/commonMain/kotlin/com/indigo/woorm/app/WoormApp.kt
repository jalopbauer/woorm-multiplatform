package com.indigo.woorm.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.indigo.woorm.app.viewmodel.AppState
import com.indigo.woorm.app.viewmodel.AppViewModel
import com.indigo.woorm.auth.AuthScreen
import com.indigo.woorm.common.loading.Loading
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
            NavigationScreen(navController) {
                Navigation(
                    navController = navController
                )
            }
        }
    }
}