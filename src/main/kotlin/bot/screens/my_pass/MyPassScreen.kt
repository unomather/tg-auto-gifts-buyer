package bot.screens.my_pass

import bot.data.KeyboardButton
import bot.data.KeyboardMarkup
import bot.screens.BaseScreen
import bot.screens.ScreenTag.MyPassScreenTag.*
import bot.screens.ScreenTag.StartScreenTag.MyPass

class MyPassScreen: BaseScreen(MyPass) {
    /**
     * KEYBOARD
     */
    override val keyboard = KeyboardMarkup(
        keyboard = listOf(
            listOf(KeyboardButton(AutoTracking.tag)),
            listOf(KeyboardButton(AutoPurchase.tag)),
            listOf(KeyboardButton(BackToMenu.tag))
        )
    )

    /**
     * TEXT
     */
    override suspend fun buildMessage() = buildString {
        append("О чём речь?")
        appendLine()
        appendLine()
        append("Отслеживание:")
        appendLine()
        append("Выйдет новый подарок - бот сразу пришлёт уведомление.")
        appendLine()
        appendLine()
        append("Автозакуп:")
        appendLine()
        append("Отслеживание + мгновенная покупка.")
        appendLine()
        append("Предварительно нужно закинуть звёзды на баланс бота.")
        appendLine()
        append("Будем покупать подарки до тех пор, пока не упрёмся в лимит или пока не кончатся звёзды.")
    }
}