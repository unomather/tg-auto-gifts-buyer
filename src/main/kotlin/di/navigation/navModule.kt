package di.navigation

import bot.navigation.CallbackDataParser
import bot.navigation.Navigator
import bot.navigation.SessionStore
import bot.navigation.UpdateRouter
import org.koin.dsl.module

val moduleNavigation = module {
    single { SessionStore() }
    single { CallbackDataParser() }
    single { Navigator(get(), get(), get(), get(), get(), get()) }
    single { UpdateRouter(get(), get(), get(), get(), get(), get()) }
}