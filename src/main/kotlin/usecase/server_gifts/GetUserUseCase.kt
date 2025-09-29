package usecase.server_gifts

import api.server_gifts.data.User
import api.server_gifts.data.UserIdRequest
import core.usecase.UseCase
import repository.server_gifts.ServerGiftsRepository

interface GetUserUseCase: UseCase<UserIdRequest, User>

internal class GetUserUseCaseImpl(
    private val repository: ServerGiftsRepository
): GetUserUseCase {
    override suspend fun invoke(input: UserIdRequest) = repository.getUser(input)
}