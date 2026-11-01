package com.indigo.woorm.navigation

/**
 * Receives deep-link URIs opened at the OS level.
 *
 * The OS can deliver a URI before (or without) the main composable being up,
 * so platform entries call [onNewUri] — iOS via the `UIApplication.open` hook
 * in iosApp (Swift `UIApplicationDelegateAdaptor`); Android needs no
 * equivalent: androidx navigation consumes the Activity intent itself.
 *
 * The URI is cached until a listener is set, so cold launches deep-link into
 * the app even when a login screen still has to come up first (the cached
 * navigate fires as soon as an authenticated NavHost exists).
 */
object ExternalUriHandler {
    private var cached: String? = null

    var listener: ((uri: String) -> Unit)? = null
        set(value) {
            field = value
            if (value != null) {
                cached?.let(value)
                cached = null
            }
        }

    fun onNewUri(uri: String) {
        cached = uri
        listener?.invoke(uri)
        cached = null
    }
}
