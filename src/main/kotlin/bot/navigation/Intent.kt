package bot.navigation

import bot.model.PaymentItem
import bot.screens.ScreenTag

sealed class Intent {
    data class ToScreen(val tag: ScreenTag) : Intent()
    data class ToOverlay(val item: PaymentItem) : Intent()
    data class BackToScreenFromOverlay(val tag: ScreenTag) : Intent()
}