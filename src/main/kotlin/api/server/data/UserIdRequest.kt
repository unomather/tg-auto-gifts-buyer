package api.server.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserIdRequest(
    @SerialName("user_id")
    val userId: Int
)