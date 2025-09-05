package bot

import bot.screens.ScreenTag
import java.util.concurrent.ConcurrentHashMap

object UiStore {
    private val rootByChat = ConcurrentHashMap<Long, Long>()
    private val tagByChat  = ConcurrentHashMap<Long, ScreenTag>()
    private val lastInvoiceMsgId = ConcurrentHashMap<Long, Long>()

    fun setRoot(chatId: Long, messageId: Long) { rootByChat[chatId] = messageId }
    fun root(chatId: Long): Long? = rootByChat[chatId]

    fun setTag(chatId: Long, tag: ScreenTag) { tagByChat[chatId] = tag }
    fun tag(chatId: Long): ScreenTag? = tagByChat[chatId]

    fun setInvoice(chatId: Long, msgId: Long) { lastInvoiceMsgId[chatId] = msgId }
    fun popInvoice(chatId: Long): Long? = lastInvoiceMsgId.remove(chatId)
}