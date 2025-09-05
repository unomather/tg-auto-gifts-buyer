package core.extensions

import api.telegram.data.LabeledPrice
import api.telegram.data.SendInvoiceRequest
import bot.model.PaymentItem

fun PaymentItem.buildInvoice(chatId: Long, callbackId: String) = SendInvoiceRequest(
    chatId = chatId,
    title = title,
    description = "Доступ к автоотслеживанию подарков",
    payload = "${callbackId}:${chatId}:${System.currentTimeMillis()}",
    currency = "XTR",
    prices = listOf(LabeledPrice(label = title, amount = price))
)