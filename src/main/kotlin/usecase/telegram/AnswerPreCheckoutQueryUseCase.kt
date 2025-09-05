package usecase.telegram

import api.telegram.data.AnswerPreCheckoutQueryRequest
import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface AnswerPreCheckoutQueryUseCase: UseCase<AnswerPreCheckoutQueryRequest, Unit>

internal class AnswerPreCheckoutQueryUseCaseImpl(
    private val repository: TelegramRepository
): AnswerPreCheckoutQueryUseCase {
    override suspend fun invoke(input: AnswerPreCheckoutQueryRequest) = repository.answerPreCheckoutQuery(input)
}