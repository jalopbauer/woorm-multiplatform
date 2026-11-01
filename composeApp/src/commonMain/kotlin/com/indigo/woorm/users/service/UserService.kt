package com.indigo.woorm.users.service

import com.indigo.woorm.users.repository.UserPreferencesRepository
import com.indigo.woorm.users.service.dtos.CreateUser
import com.indigo.woorm.users.service.dtos.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class UserService(
    private val client: HttpClient,
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend fun register(username: String): Result<User> = runCatching {
        val response = client.post(Users()) {
            setBody(CreateUser(username))
        }
        if (response.status != HttpStatusCode.OK) {
            error("Registration failed (${response.status.value})")
        }
        val user = response.body<User>()
        userPreferencesRepository.saveUserData(user)
        user
    }

    suspend fun login(id: String): Result<User> = runCatching {
        val response = client.get(Users.Id(id = id))
        if (response.status != HttpStatusCode.OK) {
            error("Login failed (${response.status.value})")
        }
        val user = response.body<User>()
        userPreferencesRepository.saveUserData(user)
        user
    }
}
