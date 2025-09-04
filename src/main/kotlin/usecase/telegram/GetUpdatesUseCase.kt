package usecase.telegram

import api.telegram.data.Update
import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface GetUpdatesUseCase: UseCase<Long?, List<Update>>

internal class GetUpdatesUseCaseImpl(
    private val repository: TelegramRepository
): GetUpdatesUseCase {
    override suspend fun invoke(input: Long?) = repository.getUpdates(input)
}