package bot.screens

import bot.data.EditMessageRequest
import bot.data.SendMessageRequest
import bot.screens.my_pass.MyPassScreen
import bot.screens.start.StartScreen

class ScreensManager(
    startScreen: StartScreen,
    myPassScreen: MyPassScreen
) {
    /**
     * SCREENS
     */
    private val allScreens = listOf(startScreen, myPassScreen)

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