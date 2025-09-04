package bot.screens

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
    suspend fun getScreenRequest(chatId: Long, tagString: String) = allScreens
        .find { screen ->
            screen.tag == allScreenTags.find { it.tag == tagString }
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