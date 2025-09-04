package api.stars

import api.base.BaseApi
import api.base.HttpRequestType.GET
import api.stars.data.StarsAmount

interface StarsApi {
    suspend fun getStartOnBalance(): StarsAmount
}

internal class StarsApiImpl: BaseApi(), StarsApi {
    override suspend fun getStartOnBalance() = makeHttpRequest<StarsAmount>(
        type = GET,
        route = "getMyStarBalance"
    )
}