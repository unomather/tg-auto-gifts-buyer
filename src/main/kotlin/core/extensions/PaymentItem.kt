package core.extensions

import api.telegram.data.LabeledPrice
import api.telegram.data.SendInvoiceRequest
import bot.data.KeyboardButton
import bot.data.KeyboardMarkup
import bot.model.PaymentItem
import bot.screens.ScreenTag

context(screenTag: ScreenTag) fun PaymentItem.buildInvoice(chatId: Long) = SendInvoiceRequest(
    chatId = chatId,
    title = title,
    description = "Доступ к автоотслеживанию подарков",
    payload = "${screenTag.callbackId}:${chatId}:${System.currentTimeMillis()}",
    currency = "XTR",
    prices = listOf(LabeledPrice(label = title, amount = price)),
    replyMarkup = KeyboardMarkup(
        keyboard = listOf(
            listOf(KeyboardButton(text = "Заплатить ⭐$price", pay = true)),
            listOf(KeyboardButton(text = "Назад", callbackData = "invoice_back_${navigateBackTo.callbackId}"))
        )
    )
)