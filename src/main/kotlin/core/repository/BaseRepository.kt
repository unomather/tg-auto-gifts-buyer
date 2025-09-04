package core.repository

import core.dispatchers.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseRepository: KoinComponent {
    protected val dispatchers by inject<AppDispatchers>()

    protected suspend inline fun <reified T> io(crossinline callback: suspend CoroutineScope.() -> T): T {
        return withContext(dispatchers.io) {
            callback()
        }
    }
}