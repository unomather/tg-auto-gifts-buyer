package core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchersImpl(
    override val io: CoroutineDispatcher = Dispatchers.IO,
    override val default: CoroutineDispatcher = Dispatchers.Default
): AppDispatchers