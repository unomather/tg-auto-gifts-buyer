package bot.screens

import bot.data.SendMessageRequest
import bot.screens.start.StartScreen
import kotlinx.coroutines.CoroutineScope

class ScreensManager(startScreen: StartScreen) {
    /**
     * SCREENS
     */
    private val allScreens = listOf<BaseScreen>(startScreen)

    /**
     * SCREEN REQUEST
     */
    suspend fun getScreenRequest(chatId: Long, tagString: String) = allScreens
        .find { screen ->
            screen.tag == ScreenTag.entries.find { it.tag == tagString }
        }
        ?.let { screen ->
            SendMessageRequest(
                chatId = chatId,
                text = screen.buildMessage(),
                replyKeyboardMarkup = screen.keyboard
            )
        }
        ?: throw IllegalStateException("Can't parse a message with chatId = $chatId, tagString = $tagString")
}