import api.telegram.data.AnswerPreCheckoutQueryRequest
import api.telegram.data.CallbackQuery
import bot.UiStore
import bot.data.AnswerCallbackQueryRequest
import bot.data.DeleteMessageRequest
import bot.model.PaymentItem
import bot.screens.ScreenTag
import bot.screens.ScreensManager
import bot.screens.allScreenTags
import core.app_scope.AppScope
import core.extensions.buildInvoice
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
import usecase.telegram.*
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
    val deleteMessageUseCase by inject<DeleteMessageUseCase>()
    val answerPreCheckoutQueryUseCase by inject<AnswerPreCheckoutQueryUseCase>()
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
                    update.preCheckoutQuery?.let { preCheckoutQuery ->
                        val request = AnswerPreCheckoutQueryRequest(id = preCheckoutQuery.id, ok = true)
                        answerPreCheckoutQueryUseCase(request)
                    }
                    update.message?.let { message ->
                        message.successfulPayment?.let {
                            UiStore.popInvoice(message.chat.id)?.let { invoiceMessageId ->
                                val request = DeleteMessageRequest(chatId = message.chat.id, messageId = invoiceMessageId)
                                deleteMessageUseCase(request)
                            }
                        }
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
        val sendMessageRequest = screensManager.buildSend(chatId, ScreenTag.StartTag.tag)
        val message = sendMessageUseCase(sendMessageRequest)
        UiStore.setRoot(chatId, message.messageId)
        UiStore.setTag(chatId, ScreenTag.StartTag)
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
    val sendInvoiceUseCase by inject<SendInvoiceUseCase>()
    val deleteMessageUseCase by inject<DeleteMessageUseCase>()

    appScope.scope.launch {
        val chatId = callbackQuery.message?.chat?.id ?: return@launch
        val messageId = callbackQuery.message.messageId   // это может быть инвойс
        val tagString = callbackQuery.data ?: return@launch

        val isFromInvoice = tagString.startsWith("invoice_back_")
        val tagStringWithoutPrefix = if (isFromInvoice) tagString.removePrefix("invoice_back_") else tagString
        val tag = allScreenTags.find { it.tag == tagStringWithoutPrefix || it.callbackId == tagStringWithoutPrefix } ?: return@launch

        if (tag is PaymentItem) {
            val invoiceRequest = with(tag) { buildInvoice(chatId) }
            val invoice = sendInvoiceUseCase(invoiceRequest)
            UiStore.setInvoice(chatId, invoice.messageId)

            val answerCallbackQueryRequest = AnswerCallbackQueryRequest(callbackQuery.id, text = "Открываю оплату…")
            answerCallbackQueryUseCase(answerCallbackQueryRequest)

            return@launch
        }

        if (isFromInvoice) {
            deleteMessageUseCase(DeleteMessageRequest(chatId, messageId))
            val current = UiStore.tag(chatId)
            if (current?.callbackId == tag.callbackId) {
                val answerCallbackQueryRequest = AnswerCallbackQueryRequest(id = callbackQuery.id)
                answerCallbackQueryUseCase(answerCallbackQueryRequest)
                return@launch
            }
        }
        val targetMsgId = if (isFromInvoice) (UiStore.root(chatId) ?: return@launch) else messageId
        val editMessageRequest = screensManager.buildEdit(chatId, targetMsgId, tag.tag)
        editMessageUseCase(editMessageRequest)
        UiStore.setTag(chatId, tag)

        val answerCallbackQueryRequest = AnswerCallbackQueryRequest(id = callbackQuery.id)
        answerCallbackQueryUseCase(answerCallbackQueryRequest)
    }
}

private fun withJitter(ms: Long): Long {
    val j = (ms * 20) / 100
    return ms + Random.nextLong(-j, j + 1)
}