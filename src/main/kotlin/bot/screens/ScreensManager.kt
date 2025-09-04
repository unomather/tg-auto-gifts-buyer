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
    suspend fun getScreenRequest(chatId: Long, tagString: String): SendMessageRequest {
        val targetTag = allScreenTags.firstOrNull { it.tag == tagString } ?: throw IllegalStateException(
            "Unknown tag: $tagString"
        )
        val screen = allScreens.firstOrNull { targetTag in it.tag } ?: throw IllegalStateException(
            "No screen found for tag: $tagString (chatId=$chatId)"
        )
        return SendMessageRequest(
            chatId = chatId,
            text = screen.buildMessage(),
            replyKeyboardMarkup = screen.keyboard
        )
    }
}