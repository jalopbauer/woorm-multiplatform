package com.indigo.woorm

import com.indigo.woorm.app.viewmodel.AppViewModel
import com.indigo.woorm.auth.AuthViewModel
import com.indigo.woorm.bookClubs.detail.BookClubDetailViewModel
import com.indigo.woorm.bookClubs.invite.BookClubInviteViewModel
import com.indigo.woorm.bookClubs.list.BookClubsViewModel
import com.indigo.woorm.bookClubs.service.BookClubService
import com.indigo.woorm.books.detail.viewModel.BookDetailViewModel
import com.indigo.woorm.books.search.SearchBooksUseCase
import com.indigo.woorm.books.search.SearchViewModel
import com.indigo.woorm.books.service.BookService
import com.indigo.woorm.goals.service.GoalService
import com.indigo.woorm.goals.viewModel.GoalsViewModel
import com.indigo.woorm.home.viewModel.HomeViewModel
import com.indigo.woorm.httpClient.userHeaderPlugin
import com.indigo.woorm.library.LibraryViewModel
import com.indigo.woorm.library.SearchLibraryBooksUseCase
import com.indigo.woorm.library.service.LibraryBookService
import com.indigo.woorm.library.service.dto.libraryBookSerializersModule
import com.indigo.woorm.profile.ProfileViewModel
import com.indigo.woorm.shelves.viewModel.ShelvesViewModel
import com.indigo.woorm.users.repository.UserPreferencesRepository
import com.indigo.woorm.users.service.UserService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.io.IOException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { createDataStore() }
    single { UserPreferencesRepository(get()) }

    single {
        HttpClient(httpClientEngine()) {
            defaultRequest {
                url(AppConfig.BASE_URL)
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        serializersModule = libraryBookSerializersModule
                    }
                )
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 30_000
            }

            install(HttpRequestRetry) {
                // Same policy as the old OkHttp RetryInterceptor: 502/503/504 or
                // network failures, max 5 retries, exponential 1s–8s backoff.
                maxRetries = 5
                retryIf { _, response ->
                    response.status.value in setOf(502, 503, 504)
                }
                retryOnExceptionIf { _, cause ->
                    cause is IOException
                }
                delayMillis(false) { retry ->
                    minOf(1_000L shl (retry - 1), 8_000L)
                }
            }

            install(userHeaderPlugin(get()))

            install(Resources)
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP_CLIENT: $message")
                    }
                }
                level = LogLevel.BODY
            }
        }
    }
    singleOf(::BookService)
    singleOf(::SearchBooksUseCase)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SearchViewModel)
    singleOf(::LibraryBookService)
    singleOf(::SearchLibraryBooksUseCase)
    viewModelOf(::LibraryViewModel)
    viewModelOf(::HomeViewModel)
    singleOf(::GoalService)
    viewModelOf(::BookClubsViewModel)
    singleOf(::BookClubService)
    viewModelOf(::BookClubDetailViewModel)
    viewModelOf(::BookClubInviteViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::ShelvesViewModel)
    viewModelOf(::GoalsViewModel)
    viewModelOf(::AppViewModel)
    single { UserService(get(), get()) }
}
