package bot.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    @SerialName("chat_id")
    val chatId: Long,
    @SerialName("text")
    val text: String,
    @SerialName("reply_markup")
    val replyKeyboardMarkup: KeyboardMarkup
)