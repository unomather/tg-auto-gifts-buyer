package di.usecase

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import usecase.gifts.GetGiftsUseCase
import usecase.gifts.GetGiftsUseCaseImpl
import usecase.stars.GetStarsOnBalanceUseCase
import usecase.stars.GetStarsOnBalanceUseCaseImpl
import usecase.telegram.AnswerCallbackQueryUseCase
import usecase.telegram.AnswerCallbackQueryUseCaseImpl
import usecase.telegram.AnswerPreCheckoutQueryUseCase
import usecase.telegram.AnswerPreCheckoutQueryUseCaseImpl
import usecase.telegram.DeleteMessageUseCase
import usecase.telegram.DeleteMessageUseCaseImpl
import usecase.telegram.DeleteWebHookUseCase
import usecase.telegram.DeleteWebHookUseCaseImpl
import usecase.telegram.EditMessageUseCase
import usecase.telegram.EditMessageUseCaseImpl
import usecase.telegram.GetUpdatesUseCase
import usecase.telegram.GetUpdatesUseCaseImpl
import usecase.telegram.SendInvoiceUseCase
import usecase.telegram.SendInvoiceUseCaseImpl
import usecase.telegram.SendMessageUseCase
import usecase.telegram.SendMessageUseCaseImpl

val moduleUseCase = module {
    /**
     * GIFTS
     */
    factoryOf(::GetGiftsUseCaseImpl) { bind<GetGiftsUseCase>() }
    /**
     * STARS
     */
    factoryOf(::GetStarsOnBalanceUseCaseImpl) { bind<GetStarsOnBalanceUseCase>() }
    /**
     * TELEGRAM
     */
    factoryOf(::DeleteWebHookUseCaseImpl) { bind<DeleteWebHookUseCase>() }
    factoryOf(::GetUpdatesUseCaseImpl) { bind<GetUpdatesUseCase>() }
    factoryOf(::AnswerCallbackQueryUseCaseImpl) { bind<AnswerCallbackQueryUseCase>() }
    factoryOf(::SendMessageUseCaseImpl) { bind<SendMessageUseCase>() }
    factoryOf(::EditMessageUseCaseImpl) { bind<EditMessageUseCase>() }
    factoryOf(::DeleteMessageUseCaseImpl) { bind<DeleteMessageUseCase>() }
    factoryOf(::SendInvoiceUseCaseImpl) { bind<SendInvoiceUseCase>() }
    factoryOf(::AnswerPreCheckoutQueryUseCaseImpl) { bind<AnswerPreCheckoutQueryUseCase>() }
}