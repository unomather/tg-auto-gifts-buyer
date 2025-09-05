package api.telegram

import api.base.ApiParameter
import api.base.BaseApi
import api.base.HttpRequestType.GET
import api.base.HttpRequestType.POST
import api.telegram.data.AnswerPreCheckoutQueryRequest
import api.telegram.data.Message
import api.telegram.data.SendInvoiceRequest
import api.telegram.data.Update
import bot.data.AnswerCallbackQueryRequest
import bot.data.DeleteMessageRequest
import bot.data.EditMessageRequest
import bot.data.SendMessageRequest
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject

interface TelegramApi {
    suspend fun deleteWebHook()
    suspend fun getUpdates(offset: Long?): List<Update>
    suspend fun sendMessage(request: SendMessageRequest): Message
    suspend fun editMessageText(request: EditMessageRequest): Message
    suspend fun answerCallbackQuery(request: AnswerCallbackQueryRequest)
    suspend fun deleteMessage(request: DeleteMessageRequest)
    suspend fun sendInvoice(request: SendInvoiceRequest): Message
    suspend fun answerPreCheckoutQuery(request: AnswerPreCheckoutQueryRequest)
}

internal class TelegramApiImpl: BaseApi(), TelegramApi {
    override suspend fun deleteWebHook() = makeHttpRequest<Unit>(
        type = GET,
        route = "deleteWebhook",
        parameters = listOf(
            ApiParameter(name = "drop_pending_updates", data = "true")
        )
    )

    override suspend fun getUpdates(offset: Long?) = makeHttpRequest<List<Update>>(
        type = POST,
        route = "getUpdates",
        bodyString = run {
            val body = buildJsonObject {
                put("timeout", JsonPrimitive(25))
                if (offset != null) {
                    put("offset", JsonPrimitive(offset))
                }
                put(
                    key = "allowed_updates",
                    element = buildJsonArray {
                        add(JsonPrimitive("message"))
                        add(JsonPrimitive("callback_query"))
                        add(JsonPrimitive("pre_checkout_query"))
                    }
                )
            }
            body.toString()
        }
    )

    override suspend fun sendMessage(request: SendMessageRequest) = makeHttpRequest<Message>(
        type = POST,
        route = "sendMessage",
        bodyString = json.encodeToString(request)
    )

    override suspend fun editMessageText(request: EditMessageRequest) = makeHttpRequest<Message>(
        type = POST,
        route = "editMessageText",
        bodyString = json.encodeToString(request)
    )

    override suspend fun answerCallbackQuery(request: AnswerCallbackQueryRequest) = makeHttpRequest<Unit>(
        type = POST,
        route = "answerCallbackQuery",
        bodyString = json.encodeToString(request)
    )

    override suspend fun deleteMessage(request: DeleteMessageRequest) = makeHttpRequest<Unit>(
        type = POST,
        route = "deleteMessage",
        bodyString = json.encodeToString(request)
    )

    override suspend fun sendInvoice(request: SendInvoiceRequest) = makeHttpRequest<Message>(
        type = POST,
        route = "sendInvoice",
        bodyString = json.encodeToString(request)
    )

    override suspend fun answerPreCheckoutQuery(request: AnswerPreCheckoutQueryRequest) = makeHttpRequest<Unit>(
        type = POST,
        route = "answerPreCheckoutQuery",
        bodyString = json.encodeToString(request)
    )
}