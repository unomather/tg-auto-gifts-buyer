package bot.navigation

import bot.model.PaymentItem
import bot.screens.allScreenTags

class CallbackDataParser {
    fun parse(data: String): Intent? {
        if (data.startsWith("invoice_back_")) {
            val target = data.removePrefix("invoice_back_")
            val tag = allScreenTags.firstOrNull { it.callbackId == target || it.tag == target } ?: return null
            return Intent.BackToScreenFromOverlay(tag)
        }
        val tag = allScreenTags.firstOrNull { it.callbackId == data || it.tag == data } ?: return null
        return if (tag is PaymentItem) {
            Intent.ToOverlay(tag)
        } else {
            Intent.ToScreen(tag)
        }
    }
}