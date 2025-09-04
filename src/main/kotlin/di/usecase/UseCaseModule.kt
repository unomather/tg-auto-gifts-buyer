package di.usecase

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import usecase.gifts.GetGiftsUseCase
import usecase.gifts.GetGiftsUseCaseImpl
import usecase.stars.GetStarsOnBalanceUseCase
import usecase.stars.GetStarsOnBalanceUseCaseImpl
import usecase.telegram.DeleteWebHookUseCase
import usecase.telegram.DeleteWebHookUseCaseImpl
import usecase.telegram.GetUpdatesUseCase
import usecase.telegram.GetUpdatesUseCaseImpl
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
    factoryOf(::GetUpdatesUseCaseImpl) { bind<GetUpdatesUseCase>() }
    factoryOf(::SendMessageUseCaseImpl) { bind<SendMessageUseCase>() }
    factoryOf(::DeleteWebHookUseCaseImpl) { bind<DeleteWebHookUseCase>() }
}