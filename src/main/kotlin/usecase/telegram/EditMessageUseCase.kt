package usecase.telegram

import api.telegram.data.Message
import bot.data.EditMessageRequest
import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface EditMessageUseCase: UseCase<EditMessageRequest, Message>

internal class EditMessageUseCaseImpl(
    private val repository: TelegramRepository
): EditMessageUseCase {
    override suspend fun invoke(input: EditMessageRequest) = repository.editMessageText(input)
}