package repository.server_auto_accept

import api.server_auto_accept.AcceptApplicationRequest
import api.server_auto_accept.ServerAutoAcceptApi
import core.repository.BaseRepository

interface ServerAutoAcceptRepository {
    suspend fun acceptApplication(request: AcceptApplicationRequest)
}

internal class ServerAutoAcceptRepositoryImpl(
    private val api: ServerAutoAcceptApi
): BaseRepository(), ServerAutoAcceptRepository {
    override suspend fun acceptApplication(request: AcceptApplicationRequest) {
        api.acceptApplication(request)
    }
}