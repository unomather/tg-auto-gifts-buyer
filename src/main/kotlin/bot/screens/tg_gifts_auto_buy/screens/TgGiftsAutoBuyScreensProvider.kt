package bot.screens.tg_gifts_auto_buy.screens

import bot.screens.BaseScreensProvider
import bot.screens.tg_gifts_auto_buy.screens.my_pass.MyPassScreenGifts
import bot.screens.tg_gifts_auto_buy.screens.start.StartScreenGifts

interface TgGiftsAutoBuyScreensProvider: BaseScreensProvider

internal class TgGiftsAutoBuyScreensProviderImpl(
    private val startScreenGifts: StartScreenGifts,
    private val myPassScreenGifts: MyPassScreenGifts
): TgGiftsAutoBuyScreensProvider {
    override fun provide() = listOf(
        startScreenGifts,
        myPassScreenGifts
    )
}