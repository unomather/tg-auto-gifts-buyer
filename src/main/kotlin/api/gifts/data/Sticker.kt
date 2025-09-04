package api.gifts.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
    @SerialName("emoji")
    val emoji: String
)