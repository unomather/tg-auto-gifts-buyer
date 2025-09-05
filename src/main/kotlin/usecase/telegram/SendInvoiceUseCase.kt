package usecase.telegram

import api.telegram.data.Message
import api.telegram.data.SendInvoiceRequest
import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface SendInvoiceUseCase: UseCase<SendInvoiceRequest, Message>

internal class SendInvoiceUseCaseImpl(
    private val repository: TelegramRepository
): SendInvoiceUseCase {
    override suspend fun invoke(input: SendInvoiceRequest) = repository.sendInvoice(input)
}