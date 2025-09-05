package bot

import bot.screens.ScreenTag
import java.util.concurrent.ConcurrentHashMap

object UiStore {
    private val rootByChat = ConcurrentHashMap<Long, Long>()
    private val tagByChat  = ConcurrentHashMap<Long, ScreenTag>()

    fun setRoot(chatId: Long, messageId: Long) { rootByChat[chatId] = messageId }
    fun root(chatId: Long): Long? = rootByChat[chatId]

    fun setTag(chatId: Long, tag: ScreenTag) { tagByChat[chatId] = tag }
    fun tag(chatId: Long): ScreenTag? = tagByChat[chatId]
}