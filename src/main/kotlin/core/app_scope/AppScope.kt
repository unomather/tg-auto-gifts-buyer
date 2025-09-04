package core.app_scope

import core.dispatchers.AppDispatchers
import kotlinx.coroutines.CoroutineScope

interface AppScope {
    val scope: CoroutineScope
}

internal class AppScopeImpl(dispatcher: AppDispatchers): AppScope {
    override val scope: CoroutineScope = CoroutineScope(dispatcher.io)
}