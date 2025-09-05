package bot.navigation

import bot.data.DeleteMessageRequest
import bot.model.PaymentItem
import bot.navigation.Intent.ToOverlay
import bot.navigation.Intent.BackToScreenFromOverlay
import bot.navigation.Intent.ToScreen
import bot.screens.ScreenTag
import bot.screens.ScreensManager
import core.extensions.buildInvoice
import usecase.telegram.DeleteMessageUseCase
import usecase.telegram.EditMessageUseCase
import usecase.telegram.SendInvoiceUseCase
import usecase.telegram.SendMessageUseCase

class Navigator(
    private val session: SessionStore,
    private val screensManager: ScreensManager,
    private val sendMessageUseCase: SendMessageUseCase,
    private val editMessageUseCase: EditMessageUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val sendInvoiceUseCase: SendInvoiceUseCase
) {
    suspend fun perform(chatId: Long, intent: Intent, sourceMessageId: Long? = null) {
        when (intent) {
            is ToScreen -> openScreen(chatId, intent.tag)
            is ToOverlay -> showOverlay(chatId, intent.item)
            is BackToScreenFromOverlay -> closeOverlayAndBack(chatId, intent.tag, sourceMessageId)
        }
    }

    private suspend fun openScreen(chatId: Long, tag: ScreenTag) {
        val anchor = session.anchor(chatId)
        if (anchor == null) {
            val sendMessageRequest = screensManager.buildSend(chatId = chatId, tagString = tag.tag)
            val message = sendMessageUseCase(sendMessageRequest)
            session.setAnchor(chatId, message.messageId)
        } else {
            val editMessageRequest = screensManager.buildEdit(chatId = chatId, messageId = anchor, tagString = tag.tag)
            editMessageUseCase(editMessageRequest)
        }
        session.setCurrentTag(chatId, tag)
    }

    private suspend fun showOverlay(chatId: Long, item: PaymentItem) {
        val screenTag = item as ScreenTag
        val sendInvoiceRequest = with(screenTag) { item.buildInvoice(chatId) }
        val message = sendInvoiceUseCase(sendInvoiceRequest)
        session.setOverlay(chatId = chatId, msgId = message.messageId)
    }

    private suspend fun closeOverlayAndBack(
        chatId: Long,
        backTo: ScreenTag,
        sourceMessageId: Long?
    ) {
        val messageIdToDelete = sourceMessageId ?: session.popOverlay(chatId)
        if (messageIdToDelete != null) {
            val deleteMessageRequest = DeleteMessageRequest(chatId = chatId, messageId = messageIdToDelete)
            deleteMessageUseCase(deleteMessageRequest)
        }
        openScreen(chatId, backTo)
    }
}