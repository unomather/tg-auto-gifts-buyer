package repository.server_gifts

import api.server_gifts.ServerGiftsApi
import api.server_gifts.data.User
import api.server_gifts.data.UserIdRequest
import core.repository.BaseRepository

interface ServerGiftsRepository {
    suspend fun getUser(request: UserIdRequest): User
    suspend fun updateUser(user: User)
}

internal class ServerGiftsRepositoryImpl(
    private val api: ServerGiftsApi
): BaseRepository(), ServerGiftsRepository {
    override suspend fun getUser(request: UserIdRequest) = io {
        api.getUser(request)
    }

    override suspend fun updateUser(user: User) = io {
        api.updateUser(user)
    }
}