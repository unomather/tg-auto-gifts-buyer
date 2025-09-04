package bot.screens.start

import bot.data.KeyboardButton
import bot.data.KeyboardMarkup
import bot.screens.BaseScreen
import bot.screens.ScreenTag.MyPassScreenTag
import bot.screens.ScreenTag.StartScreenTag.*
import bot.screens.ScreenTag.StartTag
import core.extensions.runCatchingApp
import usecase.gifts.GetGiftsUseCase
import usecase.stars.GetStarsOnBalanceUseCase

class StartScreen(
    private val getGiftsUseCase: GetGiftsUseCase,
    private val getStarsOnBalanceUseCase: GetStarsOnBalanceUseCase,
): BaseScreen(
    StartTag,
    MyPassScreenTag.BackToMenu
) {
    /**
     * KEYBOARD
     */
    override val keyboard = KeyboardMarkup(
        keyboard = listOf(
            listOf(KeyboardButton(MyPass.tag)),
            listOf(KeyboardButton(SetTrackingAndAutoPurchase.tag)),
            listOf(KeyboardButton(Faq.tag))
        )
    )

    /**
     * TEXT
     */
    override suspend fun buildMessage(): String {
        val gifts = loadGifts()
        val balance = loadStarsAmount()
        return buildString {
            append("Привет!")
            appendLine()
            append("Здесь ты можешь отслеживать выход новых подарков и автоматически их скупать.")
            appendLine()
            appendLine()
            append(balance)
            appendLine()
            appendLine()
            append("Сейчас на маркете доступны вот эти подарки:")
            appendLine()
            append(gifts)
        }
    }

    /**
     * GIFTS
     */
    private suspend fun loadGifts() = runCatchingApp {
        val gifts = getGiftsUseCase(Unit)
        buildString {
            gifts.sortedBy { it.starCount }.forEach { gift ->
                append("${gift.sticker.emoji} = ${gift.starCount} ⭐")
                appendLine()
            }
        }
    }.getOrElse {
        "При загрузке подарков произошла ошибка"
    }

    /**
     * STARS
     */
    private suspend fun loadStarsAmount() = runCatchingApp {
        val starsCount = getStarsOnBalanceUseCase(Unit)
        "Твой баланс: $starsCount ⭐"
    }.getOrElse {
        "При загрузке баланса произошла ошибка"
    }
}