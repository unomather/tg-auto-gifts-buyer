import bot.screens.ScreensManager
import core.app_scope.AppScope
import core.extensions.runCatchingApp
import di.appModules
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import usecase.telegram.DeleteWebHookUseCase
import usecase.telegram.GetUpdatesUseCase
import usecase.telegram.SendMessageUseCase
import kotlin.coroutines.cancellation.CancellationException
import kotlin.random.Random

fun main() {
    startKoin {
        modules(appModules)
        runApp()
        while (true) {
            // no-op
        }
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
        runCatchingApp {
            deleteWebHookUseCase(Unit)
        }
    }
}

private fun Koin.getUpdates(appScope: AppScope) {
    val getUpdatesUseCase by inject<GetUpdatesUseCase>()
    var offset: Long? = null
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
                    val msg = update.message ?: continue
                    val chatId = msg.chat.id
                    val text = msg.text?.trim() ?: continue
                    answerByScreen(chatId = chatId, text = text, appScope = appScope)
                }
            } catch (e: HttpRequestTimeoutException) {
                delay(250)
                continue
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                delay(1_500)
            }
        }
    }
}

private fun Koin.answerByScreen(
    chatId: Long,
    text: String,
    appScope: AppScope
) {
    val screensManager by inject<ScreensManager>()
    val sendMessageUseCase by inject<SendMessageUseCase>()
    appScope.scope.launch {
        val message = screensManager.getScreenRequest(chatId, text)
        sendMessageUseCase(message)
    }
}

private fun withJitter(ms: Long): Long {
    val j = (ms * 20) / 100
    return ms + Random.nextLong(-j, j + 1)
}