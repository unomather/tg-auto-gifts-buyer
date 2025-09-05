import bot.navigation.UpdateRouter
import core.app_scope.AppScope
import core.extensions.runCatchingApp
import di.appModules
import io.ktor.client.plugins.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import usecase.telegram.DeleteWebHookUseCase
import usecase.telegram.GetUpdatesUseCase
import kotlin.coroutines.cancellation.CancellationException

fun main() {
    startKoin {
        modules(appModules)
        runApp()
        while (true) { /* keep alive */ }
    }
}

private fun KoinApplication.runApp() = koin.run {
    val appScope by inject<AppScope>()
    appScope.scope.launch {
        deleteWebHook(appScope)
        getUpdates(appScope)
    }
}

private fun Koin.deleteWebHook(appScope: AppScope) {
    val deleteWebHookUseCase by inject<DeleteWebHookUseCase>()
    appScope.scope.launch {
        runCatchingApp { deleteWebHookUseCase(Unit) }
    }
}

private fun Koin.getUpdates(appScope: AppScope) {
    var offset: Long? = null
    val router by inject<UpdateRouter>()
    val getUpdatesUseCase by inject<GetUpdatesUseCase>()
    appScope.scope.launch {
        while (isActive) {
            try {
                ensureActive()
                val updates = getUpdatesUseCase(offset)
                if (updates.isEmpty()) {
                    delay(withJitter(1200))
                    continue
                }
                updates.forEach { update ->
                    offset = update.updateId + 1
                    router.handle(update)
                }
            } catch (_: HttpRequestTimeoutException) {
                delay(250)
            } catch (error: CancellationException) {
                throw error
            } catch (_: Throwable) {
                delay(1500)
            }
        }
    }
}

private fun withJitter(ms: Long): Long {
    val j = (ms * 20) / 100
    return ms + kotlin.random.Random.nextLong(-j, j + 1)
}