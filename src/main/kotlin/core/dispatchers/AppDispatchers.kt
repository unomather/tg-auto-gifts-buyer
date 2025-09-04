package core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatchers {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}