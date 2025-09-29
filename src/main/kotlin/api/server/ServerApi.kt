package api.server

import api.base.ApiClientSettings.Server
import api.base.ApiParameter
import api.base.BaseApi
import api.base.HttpRequestType.GET
import api.base.HttpRequestType.POST
import api.server.data.User
import api.server.data.UserIdRequest

interface ServerApi {
    suspend fun getUser(request: UserIdRequest): User
    suspend fun updateUser(user: User)
}

internal class ServerApiImpl: BaseApi(Server), ServerApi {
    override suspend fun getUser(request: UserIdRequest) = makeHttpRequest<User>(
        type = GET,
        route = "getUser",
        parameters = listOf(
            ApiParameter(name = "userId", data = request.userId)
        )
    )

    override suspend fun updateUser(user: User) = makeHttpRequest<Unit>(
        type = POST,
        route = "updateUser",
        bodyString = json.encodeToString(user)
    )
}