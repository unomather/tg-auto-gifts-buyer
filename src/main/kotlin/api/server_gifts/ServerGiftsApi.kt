package api.server_gifts

import api.base.ApiClientSettings.Server
import api.base.ApiParameter
import api.base.BaseApi
import api.base.HttpRequestType.GET
import api.base.HttpRequestType.POST
import api.server_gifts.data.User
import api.server_gifts.data.UserIdRequest

interface ServerGiftsApi {
    suspend fun getUser(request: UserIdRequest): User
    suspend fun updateUser(user: User)
}

internal class ServerGiftsApiImpl: BaseApi(Server), ServerGiftsApi {
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