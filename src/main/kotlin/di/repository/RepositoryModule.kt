package di.repository

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import repository.gifts.GiftsRepository
import repository.gifts.GiftsRepositoryImpl
import repository.server.ServerRepository
import repository.server.ServerRepositoryImpl
import repository.stars.StarsRepository
import repository.stars.StarsRepositoryImpl
import repository.telegram.TelegramRepository
import repository.telegram.TelegramRepositoryImpl

val moduleRepository = module {
    /**
     * GIFTS
     */
    factoryOf(::GiftsRepositoryImpl) { bind<GiftsRepository>() }
    /**
     * STARS
     */
    factoryOf(::StarsRepositoryImpl) { bind<StarsRepository>() }
    /**
     * TELEGRAM
     */
    factoryOf(::TelegramRepositoryImpl) { bind<TelegramRepository>() }
    /**
     * SERVER
     */
    factoryOf(::ServerRepositoryImpl) { bind<ServerRepository>() }
}