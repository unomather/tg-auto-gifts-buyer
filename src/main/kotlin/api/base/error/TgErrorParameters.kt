package api.base.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TgErrorParameters(
    @SerialName("retry_after")
    val retryAfter: Int? = null
)