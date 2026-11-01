package com.indigo.woorm

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine

/** Ktor client engine per platform (Android: OkHttp · iOS: Darwin). */
expect fun httpClientEngine(): HttpClientEngine

/** Shared user-prefs DataStore, stored in a platform-appropriate app dir. */
expect fun createDataStore(): DataStore<Preferences>

/** Opens the platform share sheet (Android chooser intent · iOS UIActivityViewController). */
expect fun shareText(title: String, text: String)
