package bot.model

import bot.screens.ScreenTag

interface PaymentItem {
    val title: String
    val description: String
    val price: Int
    val navigateBackTo: ScreenTag
}