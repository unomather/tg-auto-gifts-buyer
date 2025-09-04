package di.screens

import bot.screens.ScreensManager
import bot.screens.my_pass.MyPassScreen
import bot.screens.start.StartScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moduleScreens = module {
    /**
     * MANAGER
     */
    singleOf(::ScreensManager)
    /**
     * START
     */
    factoryOf(::StartScreen)
    /**
     * MY PASS
     */
    factoryOf(::MyPassScreen)
}