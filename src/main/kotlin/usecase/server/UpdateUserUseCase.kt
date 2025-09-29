package usecase.server

import api.server.data.User
import core.usecase.UseCase
import repository.server.ServerRepository

interface UpdateUserUseCase: UseCase<User, Unit>

internal class UpdateUserUseCaseImpl(
    private val repository: ServerRepository
): UpdateUserUseCase {
    override suspend fun invoke(input: User) = repository.updateUser(input)
}