package di.screens

import bot.screens.ScreensManager
import bot.screens.tg_gifts_auto_buy.TgGiftsAutoBuyScreensProvider
import bot.screens.tg_gifts_auto_buy.TgGiftsAutoBuyScreensProviderImpl
import bot.screens.tg_gifts_auto_buy.my_pass.MyPassScreenGifts
import bot.screens.tg_gifts_auto_buy.start.StartScreenGifts
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moduleScreens = module {
    /**
     * MANAGER
     */
    singleOf(::ScreensManager)
    /**
     * GIFTS
     */
    factoryOf(::StartScreenGifts)
    factoryOf(::MyPassScreenGifts)
    factoryOf(::TgGiftsAutoBuyScreensProviderImpl) { bind<TgGiftsAutoBuyScreensProvider>() }
}