package api.server_auto_accept

import api.base.ApiClientSettings.Server
import api.base.BaseApi
import api.base.HttpRequestType.POST
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface ServerAutoAcceptApi {
    suspend fun acceptApplication(request: AcceptApplicationRequest)
}

internal class ServerAutoAcceptApiImpl: ServerAutoAcceptApi, BaseApi(Server) {
    override suspend fun acceptApplication(request: AcceptApplicationRequest) = makeHttpRequest<Unit>(
        type = POST,
        route = "accept",
        bodyString = json.encodeToString(request)
    )
}

@Serializable
data class AcceptApplicationRequest(
    @SerialName("userId")
    val userId: Long,
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String
)