package com.indigo.woorm.httpClient

import com.indigo.woorm.users.repository.UserPreferencesRepository
import io.ktor.client.plugins.api.createClientPlugin

fun userHeaderPlugin(userPrefs: UserPreferencesRepository) = createClientPlugin("UserHeaderPlugin") {
    onRequest { request, _ ->
        val userId = userPrefs.getUserId()

        if (!userId.isNullOrBlank()) {
            request.headers.append("X-User-Id", userId)
        }
    }
}