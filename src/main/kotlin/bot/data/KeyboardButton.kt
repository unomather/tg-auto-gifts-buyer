package bot.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyboardButton(
    @SerialName("text")
    val text: String,
    @SerialName("callback_data")
    val callbackData: String
)