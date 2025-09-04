package repository.telegram

import api.telegram.TelegramApi
import api.telegram.data.Message
import api.telegram.data.Update
import bot.data.AnswerCallbackQueryRequest
import bot.data.DeleteMessageRequest
import bot.data.EditMessageRequest
import bot.data.SendMessageRequest
import core.repository.BaseRepository

interface TelegramRepository {
    suspend fun deleteWebHook()
    suspend fun getUpdates(offset: Long?): List<Update>
    suspend fun sendMessage(request: SendMessageRequest): Message
    suspend fun editMessageText(request: EditMessageRequest): Message
    suspend fun answerCallbackQuery(request: AnswerCallbackQueryRequest)
    suspend fun deleteMessage(request: DeleteMessageRequest)
}

internal class TelegramRepositoryImpl(
    private val api: TelegramApi
): BaseRepository(), TelegramRepository {
    override suspend fun deleteWebHook() = io {
        api.deleteWebHook()
    }

    override suspend fun getUpdates(offset: Long?) = io {
        api.getUpdates(offset)
    }

    override suspend fun sendMessage(request: SendMessageRequest) = io {
        api.sendMessage(request)
    }

    override suspend fun editMessageText(request: EditMessageRequest) = io {
        api.editMessageText(request)
    }

    override suspend fun answerCallbackQuery(request: AnswerCallbackQueryRequest) = io {
        api.answerCallbackQuery(request)
    }

    override suspend fun deleteMessage(request: DeleteMessageRequest) = io {
        api.deleteMessage(request)
    }
}