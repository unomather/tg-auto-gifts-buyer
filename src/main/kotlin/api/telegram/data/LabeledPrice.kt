package api.telegram.data

import bot.data.KeyboardMarkup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LabeledPrice(
    @SerialName("label")
    val label: String,
    @SerialName("amount")
    val amount: Int // для XTR: 1 = 1 ⭐
)

@Serializable
data class SendInvoiceRequest(
    @SerialName("chat_id")
    val chatId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("payload")
    val payload: String, // твой ID покупки
    @SerialName("provider_token")
    val providerToken: String = "", // Stars => пусто
    @SerialName("currency")
    val currency: String, // Stars = "XTR"
    @SerialName("prices")
    val prices: List<LabeledPrice>, // для Stars — ровно один элемент
    @SerialName("reply_markup")
    val replyMarkup: KeyboardMarkup? = null // можно не задавать
)

@Serializable
data class SuccessfulPayment(
    @SerialName("currency")
    val currency: String,
    @SerialName("total_amount")
    val totalAmount: Int,
    @SerialName("invoice_payload")
    val invoicePayload: String,
    @SerialName("telegram_payment_charge_id")
    val telegramPaymentChargeId: String,
    @SerialName("provider_payment_charge_id")
    val providerPaymentChargeId: String? = null,
    @SerialName("subscription_expiration_date")
    val subscriptionExpirationDate: Long? = null,
    @SerialName("is_recurring")
    val isRecurring: Boolean? = null,
    @SerialName("is_first_recurring")
    val isFirstRecurring: Boolean? = null
)

@Serializable
data class PreCheckoutQuery(
    @SerialName("id")
    val id: String,
    @SerialName("from")
    val from: User,
    @SerialName("currency")
    val currency: String,
    @SerialName("total_amount")
    val totalAmount: Int,
    @SerialName("invoice_payload")
    val invoicePayload: String
)

@Serializable
data class AnswerPreCheckoutQueryRequest(
    @SerialName("pre_checkout_query_id")
    val id: String,
    @SerialName("ok")
    val ok: Boolean,
    @SerialName("error_message")
    val errorMessage: String? = null
)