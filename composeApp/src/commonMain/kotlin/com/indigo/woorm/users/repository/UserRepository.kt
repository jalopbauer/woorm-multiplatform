package com.indigo.woorm.users.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.indigo.woorm.users.service.dtos.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val USERNAME_KEY = stringPreferencesKey("username")
    }

    val userIdFlow: Flow<String?> = dataStore.data.map { it[USER_ID_KEY] }
    val usernameFlow: Flow<String?> = dataStore.data.map { it[USERNAME_KEY] }

    suspend fun getUserId(): String? {
        return dataStore.data.first()[USER_ID_KEY]
    }

    suspend fun saveUserData(user: User) {
        dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = user.id
            prefs[USERNAME_KEY] = user.name
        }
    }
    
    suspend fun clearUserData() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}