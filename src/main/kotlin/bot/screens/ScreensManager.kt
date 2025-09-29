package bot.screens

import bot.data.EditMessageRequest
import bot.data.SendMessageRequest
import bot.screens.auto_accept.screens.AutoAcceptScreensProvider
import bot.screens.tg_gifts_auto_buy.allScreenTags
import bot.screens.tg_gifts_auto_buy.screens.TgGiftsAutoBuyScreensProvider

class ScreensManager(
//    provider: TgGiftsAutoBuyScreensProvider,
    provider: AutoAcceptScreensProvider
) {
    /**
     * SCREENS
     */
    private val allScreens = provider.provide()

    /**
     * SCREEN REQUEST
     */

    suspend fun buildSend(chatId: Long, tagString: String): SendMessageRequest {
        val screen = pick(tagString)
        return SendMessageRequest(
            chatId = chatId,
            text = screen.buildMessage(),
            replyKeyboardMarkup = screen.keyboard
        )
    }

    suspend fun buildEdit(chatId: Long, messageId: Long, tagString: String): EditMessageRequest {
        val screen = pick(tagString)
        return EditMessageRequest(
            chatId = chatId,
            messageId = messageId,
            text = screen.buildMessage(),
            replyMarkup = screen.keyboard
        )
    }

    private fun pick(tagString: String): BaseScreen {
        val targetTag = allScreenTags.firstOrNull { it.tag == tagString || it.callbackId == tagString }
            ?: error("Unknown tag: $tagString")
        return allScreens.firstOrNull { targetTag in it.tag } ?: error("No screen for tag: $tagString")
    }
}