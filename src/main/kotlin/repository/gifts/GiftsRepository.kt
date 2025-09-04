package repository.gifts

import api.gifts.GiftsApi
import api.gifts.data.Gift
import core.repository.BaseRepository

interface GiftsRepository {
    suspend fun getGifts(): List<Gift>
}

internal class GiftsRepositoryImpl(
    private val api: GiftsApi
): BaseRepository(), GiftsRepository {

    override suspend fun getGifts() = io {
        api.getStarGifts().gifts
    }
}