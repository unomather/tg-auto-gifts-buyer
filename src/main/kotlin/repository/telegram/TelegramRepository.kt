package repository.telegram

import api.telegram.TelegramApi
import api.telegram.data.Message
import api.telegram.data.Update
import bot.data.SendMessageRequest
import core.repository.BaseRepository

interface TelegramRepository {
    suspend fun deleteWebHook()
    suspend fun getUpdates(offset: Long?): List<Update>
    suspend fun sendMessage(request: SendMessageRequest): Message
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
}