package repository.stars

import api.stars.StarsApi
import core.repository.BaseRepository

interface StarsRepository {
    suspend fun getStartOnBalance(): Int
}

internal class StarsRepositoryImpl(
    private val api: StarsApi
): BaseRepository(), StarsRepository {
    override suspend fun getStartOnBalance() = io {
        api.getStartOnBalance().amount
    }
}