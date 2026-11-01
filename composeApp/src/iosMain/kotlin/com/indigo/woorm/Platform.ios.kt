package com.indigo.woorm

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import okio.Path.Companion.toPath
import platform.Foundation.NSHomeDirectory
import platform.UIKit.UIActivityViewController

actual fun httpClientEngine(): HttpClientEngine = Darwin.create {}

actual fun createDataStore(): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            // Documents/ always exists on iOS; no createDirectories() needed.
            NSHomeDirectory().toPath().resolve("Documents")
                .resolve("woorm_user_prefs.preferences_pb")
        }
    )

actual fun shareText(title: String, text: String) {
    val activityViewController = UIActivityViewController(
        activityItems = listOf(text),
        applicationActivities = null,
    )
    shareViewController?.presentViewController(
        activityViewController,
        animated = true,
        completion = null,
    )
}
