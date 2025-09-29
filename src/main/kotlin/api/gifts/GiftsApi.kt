package api.gifts

import api.base.ApiClientSettings.Telegram
import api.base.BaseApi
import api.base.HttpRequestType.GET
import api.gifts.data.Gifts

interface GiftsApi {
    suspend fun getStarGifts(): Gifts
}

internal class GiftsApiImpl: BaseApi(Telegram), GiftsApi {
    override suspend fun getStarGifts() = makeHttpRequest<Gifts>(
        type = GET,
        route = "getAvailableGifts"
    )
}