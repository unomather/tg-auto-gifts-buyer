package usecase.server_auto_accept

import api.server_auto_accept.AcceptApplicationRequest
import core.usecase.UseCase
import repository.server_auto_accept.ServerAutoAcceptRepository

interface AcceptApplicationUseCase: UseCase<AcceptApplicationRequest, Unit>

internal class AcceptApplicationUseCaseImpl(
    private val repository: ServerAutoAcceptRepository
): AcceptApplicationUseCase {
    override suspend fun invoke(input: AcceptApplicationRequest) {
        repository.acceptApplication(input)
    }
}