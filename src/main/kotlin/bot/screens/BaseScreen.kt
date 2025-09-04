package bot.screens

import bot.data.KeyboardMarkup
import core.app_scope.AppScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseScreen(val tag: ScreenTag): KoinComponent {
    protected val appScope by inject<AppScope>()
    abstract val keyboard: KeyboardMarkup
    abstract suspend fun buildMessage(): String
}