package usecase.server

import api.server.data.User
import api.server.data.UserIdRequest
import core.usecase.UseCase
import repository.server.ServerRepository

interface GetUserUseCase: UseCase<UserIdRequest, User>

internal class GetUserUseCaseImpl(
    private val repository: ServerRepository
): GetUserUseCase {
    override suspend fun invoke(input: UserIdRequest) = repository.getUser(input)
}