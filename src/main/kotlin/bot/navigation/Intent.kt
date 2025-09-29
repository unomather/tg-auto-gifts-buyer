package bot.navigation

import bot.model.PaymentItem
import bot.screens.BaseScreenTag

sealed class Intent {
    data class ToScreen(val tag: BaseScreenTag) : Intent()
    data class ToOverlay(val item: PaymentItem) : Intent()
    data class BackToScreenFromOverlay(val tag: BaseScreenTag) : Intent()
}