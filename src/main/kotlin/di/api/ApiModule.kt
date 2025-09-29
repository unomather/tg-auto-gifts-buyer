package di.api

import api.gifts.GiftsApi
import api.gifts.GiftsApiImpl
import api.server.ServerApi
import api.server.ServerApiImpl
import api.stars.StarsApi
import api.stars.StarsApiImpl
import api.telegram.TelegramApi
import api.telegram.TelegramApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moduleApi = module {
    /**
     * HTTP CLIENT
     */
    single {
        HttpClient(CIO.create()) {
            expectSuccess = false
            install(ContentNegotiation) {
                json(get<Json>())
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.BODY
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 35_000
                socketTimeoutMillis = 35_000
                connectTimeoutMillis = 10_000
            }

        }
    }
    /**
     * GIFTS
     */
    singleOf(::GiftsApiImpl) { bind<GiftsApi>() }
    /**
     * STARS
     */
    singleOf(::StarsApiImpl) { bind<StarsApi>() }
    /**
     * TELEGRAM
     */
    singleOf(::TelegramApiImpl) { bind<TelegramApi>() }
    /**
     * SERVER
     */
    singleOf(::ServerApiImpl) { bind<ServerApi>() }
}