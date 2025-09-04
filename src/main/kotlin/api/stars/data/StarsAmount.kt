package api.stars.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StarsAmount(
    @SerialName("amount")
    val amount: Int
)