package bot.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerCallbackQueryRequest(
    @SerialName("callback_query_id")
    val id: String,
    @SerialName("text")
    val text: String? = null,
    @SerialName("show_alert")
    val showAlert: Boolean = false
)