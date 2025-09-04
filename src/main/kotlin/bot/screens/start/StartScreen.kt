package bot.screens.start

import bot.data.KeyboardButton
import bot.data.KeyboardMarkup
import bot.screens.BaseScreen
import bot.screens.ScreenTag.START
import core.extensions.runCatchingApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import usecase.gifts.GetGiftsUseCase
import usecase.stars.GetStarsOnBalanceUseCase

class StartScreen(
    private val getGiftsUseCase: GetGiftsUseCase,
    private val getStarsOnBalanceUseCase: GetStarsOnBalanceUseCase,
): BaseScreen(START) {
    /**
     * KEYBOARD
     */
    override val keyboard = KeyboardMarkup(
        keyboard = listOf(
            listOf(KeyboardButton("Баланс"), KeyboardButton("Включить автозакуп")),
            listOf(KeyboardButton("FAQ"), KeyboardButton("Связь с админом"))
        )
    )

    /**
     * LOGIC
     */
    override suspend fun buildMessage(): String {
        val gifts = loadGifts()
        val balance = loadStarsAmount()
        return buildString {
            append("Добро пожаловать!")
            appendLine()
            append("Вас приветствует автозакуп подарков без хуйни.")
            appendLine()
            appendLine()
            append(balance)
            appendLine()
            appendLine()
            append("Сейчас на маркете свободно торгуются следующие подарки:")
            appendLine()
            append(gifts)
        }
    }

    private suspend fun loadGifts() = runCatchingApp {
        val gifts = getGiftsUseCase(Unit)
        buildString {
            gifts.sortedBy { it.starCount }.forEach { gift ->
                append("${gift.sticker.emoji} = ${gift.starCount} ⭐")
                appendLine()
            }
        }
    }.getOrElse {
        println("loadGifts error = ${it.message}")
        "При загрузке подарков произошла ошибка"
    }

    private suspend fun loadStarsAmount() = runCatchingApp {
        val starsCount = getStarsOnBalanceUseCase(Unit)
        "Ваш баланс: $starsCount звёзд"
    }.getOrElse {
        println("loadStarsAmount error = ${it.message}")
        "При загрузке баланса произошла ошибка"
    }
}