package usecase.telegram

import api.telegram.data.Message
import bot.data.SendMessageRequest
import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface SendMessageUseCase: UseCase<SendMessageRequest, Message>

internal class SendMessageUseCaseImpl(
    private val repository: TelegramRepository
): SendMessageUseCase {
    override suspend fun invoke(input: SendMessageRequest) = repository.sendMessage(input)
}