package api.server_gifts.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("userId")
    val id: Int,
    @SerialName("starBalance")
    val starBalance: Int,
    @SerialName("subscriptionType")
    val subscriptionType: Int,
    @SerialName("subscriptionValidUntil")
    val subscriptionValidUntil: Long
)