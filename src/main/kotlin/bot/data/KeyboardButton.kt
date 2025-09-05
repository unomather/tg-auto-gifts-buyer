package bot.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyboardButton(
    @SerialName("text")
    val text: String,
    @SerialName("callback_data")
    val callbackData: String? = null, // null only if pay != null
    @SerialName("pay")
    val pay: Boolean? = null // for invoice with custom keyboard
)