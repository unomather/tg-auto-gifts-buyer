package repository.server

import api.server.ServerApi
import api.server.data.User
import api.server.data.UserIdRequest
import core.repository.BaseRepository

interface ServerRepository {
    suspend fun getUser(request: UserIdRequest): User
    suspend fun updateUser(user: User)
}

internal class ServerRepositoryImpl(
    private val api: ServerApi
): BaseRepository(), ServerRepository {
    override suspend fun getUser(request: UserIdRequest) = io {
        api.getUser(request)
    }

    override suspend fun updateUser(user: User) = io {
        api.updateUser(user)
    }
}