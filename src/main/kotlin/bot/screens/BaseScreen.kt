package bot.screens

import bot.data.KeyboardMarkup
import org.koin.core.component.KoinComponent

abstract class BaseScreen(vararg val tag: ScreenTag): KoinComponent {
    abstract val keyboard: KeyboardMarkup
    abstract suspend fun buildMessage(): String
}