package usecase.telegram

import bot.data.DeleteMessageRequest
import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface DeleteMessageUseCase: UseCase<DeleteMessageRequest, Unit>

internal class DeleteMessageUseCaseImpl(
    private val repository: TelegramRepository
): DeleteMessageUseCase {
    override suspend fun invoke(input: DeleteMessageRequest) = repository.deleteMessage(input)
}