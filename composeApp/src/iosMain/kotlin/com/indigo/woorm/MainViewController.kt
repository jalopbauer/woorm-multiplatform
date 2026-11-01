package com.indigo.woorm

import androidx.compose.ui.window.ComposeUIViewController
import com.indigo.woorm.app.WoormApp
import com.indigo.woorm.ui.theme.WoormTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration
import platform.UIKit.UIViewController

/**
 * Entry for the iOS app (MainViewController.swift), and host for the share
 * sheet: common code (shareText) needs a presenting view controller, so the
 * Compose controller is stashed here at startup.
 */
internal var shareViewController: UIViewController? = null

@Suppress("FunctionName")
fun MainViewController(): UIViewController {
    val controller = ComposeUIViewController {
        KoinApplication(
            configuration = koinConfiguration(
                declaration = {
                    modules(appModule)
                }
            ),
            content = {
                WoormTheme {
                    WoormApp()
                }
            }
        )
    }
    shareViewController = controller
    return controller
}
