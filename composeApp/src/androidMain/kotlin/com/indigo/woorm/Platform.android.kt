package com.indigo.woorm

import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okio.Path.Companion.toPath
import java.io.File

/**
 * Activity context captured in [MainActivity] before Koin starts, because
 * commonMain's `startKoin` has no `androidContext()`.
 */
internal lateinit var appContext: Context

fun initAppContext(context: Context) {
    appContext = context
}

actual fun httpClientEngine(): HttpClientEngine = OkHttp.create {}

actual fun createDataStore(): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            File(appContext.filesDir, "woorm_user_prefs.preferences_pb").absolutePath.toPath()
        }
    )

actual fun shareText(title: String, text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    // NEW_TASK: shareText is launched from @Composable callbacks on
    // non-activity contexts, where startActivity without the flag throws.
    val chooser = Intent.createChooser(shareIntent, title)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    appContext.startActivity(chooser)
}
