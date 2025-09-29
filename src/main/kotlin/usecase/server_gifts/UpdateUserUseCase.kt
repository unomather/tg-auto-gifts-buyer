package usecase.server_gifts

import api.server_gifts.data.User
import core.usecase.UseCase
import repository.server_gifts.ServerGiftsRepository

interface UpdateUserUseCase: UseCase<User, Unit>

internal class UpdateUserUseCaseImpl(
    private val repository: ServerGiftsRepository
): UpdateUserUseCase {
    override suspend fun invoke(input: User) = repository.updateUser(input)
}