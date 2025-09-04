package usecase.telegram

import bot.data.AnswerCallbackQueryRequest
import core.usecase.UseCase
import repository.telegram.TelegramRepository

interface AnswerCallbackQueryUseCase: UseCase<AnswerCallbackQueryRequest, Unit>

internal class AnswerCallbackQueryUseCaseImpl(
    private val repository: TelegramRepository
): AnswerCallbackQueryUseCase {
    override suspend fun invoke(input: AnswerCallbackQueryRequest) = repository.answerCallbackQuery(input)
}