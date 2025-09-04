package usecase.stars

import core.usecase.UseCase
import repository.stars.StarsRepository

interface GetStarsOnBalanceUseCase: UseCase<Unit, Int>

internal class GetStarsOnBalanceUseCaseImpl(
    private val repository: StarsRepository
): GetStarsOnBalanceUseCase {
    override suspend fun invoke(input: Unit) = repository.getStartOnBalance()
}