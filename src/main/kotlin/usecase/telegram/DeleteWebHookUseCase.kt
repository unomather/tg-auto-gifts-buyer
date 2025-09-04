package usecase.telegram

import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface DeleteWebHookUseCase: UseCase<Unit, Unit>

internal class DeleteWebHookUseCaseImpl(
    private val repository: TelegramRepository
): DeleteWebHookUseCase {
    override suspend fun invoke(input: Unit) = repository.deleteWebHook()
}