package bot.navigation

import api.server_auto_accept.AcceptApplicationRequest
import api.telegram.data.AnswerPreCheckoutQueryRequest
import api.telegram.data.Update
import bot.data.AnswerCallbackQueryRequest
import bot.data.DeleteMessageRequest
import bot.screens.BaseStartTag
import usecase.server_auto_accept.AcceptApplicationUseCase
import usecase.telegram.AnswerCallbackQueryUseCase
import usecase.telegram.AnswerPreCheckoutQueryUseCase
import usecase.telegram.DeleteMessageUseCase

class UpdateRouter(
    private val navigator: Navigator,
    private val callbackDataParser: CallbackDataParser,
    private val sessionStore: SessionStore,
    private val answerCallbackQueryUseCase: AnswerCallbackQueryUseCase,
    private val answerPreCheckoutQueryUseCase: AnswerPreCheckoutQueryUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val acceptApplicationUseCase: AcceptApplicationUseCase
) {
    suspend fun handle(update: Update) {
        /**
         * PRE-CHECKOUT
         */
        update.preCheckoutQuery?.let { preCheckoutQuery ->
            val request = AnswerPreCheckoutQueryRequest(id = preCheckoutQuery.id, ok = true)
            answerPreCheckoutQueryUseCase(request)
            return
        }
        /**
         * MESSAGE
         */
        update.message?.let { message ->
            val chatId = message.chat.id
            message.successfulPayment?.let {
                sessionStore.popOverlay(chatId)?.let { overlayMessageId ->
                    val request = DeleteMessageRequest(chatId = chatId, messageId = overlayMessageId)
                    deleteMessageUseCase(request)
                }
                return
            }
            val text = message.text?.trim()
            if (text == BaseStartTag.tag) {
                navigator.perform(chatId, Intent.ToScreen(BaseStartTag))
                val acceptApplicationRequest = message.from?.let { from ->
                    AcceptApplicationRequest(
                        userId = from.id,
                        username = from.username.orEmpty(),
                        name = "${from.firstName.orEmpty()} ${from.lastName.orEmpty()}"
                    )
                }
                acceptApplicationRequest?.let {
                    acceptApplicationUseCase(it)
                }
                val deleteMessageRequest = DeleteMessageRequest(chatId = chatId, messageId = message.messageId)
                deleteMessageUseCase(deleteMessageRequest)
            }
            return
        }
        /**
         * CALLBACK QUERY
         */
        update.callbackQuery?.let { callbackQuery ->
            val chatId = callbackQuery.message?.chat?.id ?: return
            val messageId = callbackQuery.message.messageId
            val data = callbackQuery.data ?: return

            val intent = callbackDataParser.parse(data)
            if (intent != null) {
                navigator.perform(chatId, intent, sourceMessageId = messageId)
                val toast = if (intent is Intent.ToOverlay) "Открываю оплату…" else null
                answerCallbackQueryUseCase(AnswerCallbackQueryRequest(callbackQuery.id, text = toast))
            } else {
                answerCallbackQueryUseCase(AnswerCallbackQueryRequest(callbackQuery.id))
            }
        }
    }
}