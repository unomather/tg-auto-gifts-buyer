package bot.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyboardMarkup(
    @SerialName("keyboard")
    val keyboard: List<List<KeyboardButton>>
)