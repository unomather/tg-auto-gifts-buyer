package bot.screens.auto_accept.screens.start

import bot.data.KeyboardMarkup
import bot.screens.BaseScreen
import bot.screens.BaseStartTag

class StartScreenAutoAccept: BaseScreen(BaseStartTag) {
    /**
     * KEYBOARD
     */
    override val keyboard = KeyboardMarkup(emptyList())
    /**
     * MESSAGE
     */
    override suspend fun buildMessage(): String {
        return "Дарова"
    }
}