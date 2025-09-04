package api.base

import api.base.error.TgErrorParameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TgEnvelope<T>(
    val ok: Boolean,
    val result: T,
    val description: String? = null,
    @SerialName("error_code")
    val errorCode: Int? = null,
    val parameters: TgErrorParameters? = null
)