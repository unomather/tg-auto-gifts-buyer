package api.telegram.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    @SerialName("id")
    val id: Long,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("username")
    val username: String? = null,
    @SerialName("type")
    val type: String
)