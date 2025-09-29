package bot.screens.auto_accept.screens

import bot.screens.BaseScreensProvider
import bot.screens.auto_accept.screens.start.StartScreenAutoAccept

interface AutoAcceptScreensProvider: BaseScreensProvider

internal class AutoAcceptScreensProviderImpl(
    private val startScreenAutoAccept: StartScreenAutoAccept
): AutoAcceptScreensProvider {
    override fun provide() = listOf(startScreenAutoAccept)
}