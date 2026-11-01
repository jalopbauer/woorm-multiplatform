package com.indigo.woorm

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.indigo.woorm.app.WoormApp
import com.indigo.woorm.ui.theme.WoormTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Capture the Context before Koin starts: commonMain's appModule uses
        // initAppContext-backed createDataStore() instead of androidContext().
        initAppContext(this)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        setContent {
            KoinApplication(
                configuration = koinConfiguration(
                    declaration = {
                        androidLogger()
                        androidContext(this@MainActivity)
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
    }
}
