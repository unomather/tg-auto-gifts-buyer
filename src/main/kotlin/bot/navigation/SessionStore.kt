package bot.navigation

import bot.screens.BaseScreenTag
import java.util.concurrent.ConcurrentHashMap

class SessionStore {
    /**
     * SESSION
     */
    private data class Session(
        var anchorMessageId: Long? = null,
        var currentTag: BaseScreenTag? = null,
        var overlayMessageId: Long? = null
    )

    private val sessions = ConcurrentHashMap<Long, Session>()

    private fun getSession(chatId: Long) = sessions.getOrPut(chatId) { Session() }

    /**
     * ANCHOR
     */
    fun anchor(chatId: Long): Long? = getSession(chatId).anchorMessageId

    fun setAnchor(chatId: Long, msgId: Long) {
        getSession(chatId).anchorMessageId = msgId
    }

    /**
     * SCREEN
     */
    fun currentTag(chatId: Long): BaseScreenTag? = getSession(chatId).currentTag

    fun setCurrentTag(chatId: Long, tag: BaseScreenTag) {
        getSession(chatId).currentTag = tag
    }

    /**
     * OVERLAY
     */
    fun overlay(chatId: Long): Long? = getSession(chatId).overlayMessageId

    fun setOverlay(chatId: Long, msgId: Long) {
        getSession(chatId).overlayMessageId = msgId
    }

    fun popOverlay(chatId: Long): Long? = getSession(chatId).let { session ->
        val overlayMessageId = session.overlayMessageId
        session.overlayMessageId = null
        overlayMessageId
    }
}