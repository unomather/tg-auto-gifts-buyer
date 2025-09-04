package api.gifts.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gifts(
    @SerialName("gifts")
    val gifts: List<Gift> = emptyList()
)