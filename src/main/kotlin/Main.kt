import api.telegram.data.CallbackQuery
import bot.data.AnswerCallbackQueryRequest
import bot.data.DeleteMessageRequest
import bot.screens.ScreenTag
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
import usecase.telegram.AnswerCallbackQueryUseCase
import usecase.telegram.DeleteMessageUseCase
import usecase.telegram.DeleteWebHookUseCase
import usecase.telegram.EditMessageUseCase
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
                    update.message?.let { message ->
                        val chatId = message.chat.id
                        val text = message.text?.trim() ?: return@let
                        if (text == "/start") {
                            answerStart(chatId, appScope)
                        } else {
                            answerByText(chatId, text, message.messageId, appScope)
                        }
                    }
                    update.callbackQuery?.let { cq ->
                        answerByCallback(cq, appScope)
                    }
                }
            } catch (e: HttpRequestTimeoutException) {
                delay(250)
                continue
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                delay(1500)
            }
        }
    }
}

private fun Koin.answerStart(chatId: Long, appScope: AppScope) {
    val screensManager by inject<ScreensManager>()
    val sendMessageUseCase by inject<SendMessageUseCase>()
    appScope.scope.launch {
        val req = screensManager.buildSend(chatId, ScreenTag.StartTag.tag)
        sendMessageUseCase(req)
    }
}

private fun Koin.answerByText(chatId: Long, text: String, userMessageId: Long, appScope: AppScope) {
    val screensManager by inject<ScreensManager>()
    val sendMessageUseCase by inject<SendMessageUseCase>()
    val deleteMessageUseCase by inject<DeleteMessageUseCase>()
    appScope.scope.launch {
        val req = screensManager.buildSend(chatId, text)
        sendMessageUseCase(req)
        val request = DeleteMessageRequest(chatId, userMessageId)
        deleteMessageUseCase(request)
    }
}

private fun Koin.answerByCallback(callbackQuery: CallbackQuery, appScope: AppScope) {
    val screensManager by inject<ScreensManager>()
    val editMessageUseCase by inject<EditMessageUseCase>()
    val answerCallbackQueryUseCase by inject<AnswerCallbackQueryUseCase>()
    appScope.scope.launch {
        val chatId = callbackQuery.message?.chat?.id ?: return@launch
        val messageId = callbackQuery.message.messageId
        val tag = callbackQuery.data ?: return@launch

        val editMessageRequest = screensManager.buildEdit(chatId, messageId, tag)
        editMessageUseCase(editMessageRequest)
        val answerCallbackQueryRequest = AnswerCallbackQueryRequest(id = callbackQuery.id)
        answerCallbackQueryUseCase(answerCallbackQueryRequest)
    }
}

private fun withJitter(ms: Long): Long {
    val j = (ms * 20) / 100
    return ms + Random.nextLong(-j, j + 1)
}