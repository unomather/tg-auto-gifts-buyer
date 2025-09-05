package api.telegram.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("message_id")
    val messageId: Long,
    @SerialName("date")
    val date: Long,
    @SerialName("chat")
    val chat: Chat,
    @SerialName("text")
    val text: String? = null,
    @SerialName("from")
    val from: User? = null,
    @SerialName("successful_payment")
    val successfulPayment: SuccessfulPayment? = null
)