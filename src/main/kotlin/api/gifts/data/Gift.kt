package api.gifts.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gift(
    @SerialName("id")
    val id: Long,
    @SerialName("star_count")
    val starCount: Long,
    @SerialName("sticker")
    val sticker: Sticker
)