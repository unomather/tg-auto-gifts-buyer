package usecase.gifts

import api.gifts.data.Gift
import core.usecase.UseCase
import repository.gifts.GiftsRepository

interface GetGiftsUseCase: UseCase<Unit, List<Gift>>

class GetGiftsUseCaseImpl(
    private val repository: GiftsRepository
): GetGiftsUseCase {
    override suspend fun invoke(input: Unit) = repository.getGifts()
}