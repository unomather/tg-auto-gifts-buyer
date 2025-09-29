package bot.model

import bot.screens.BaseScreenTag

interface PaymentItem {
    val title: String
    val description: String
    val price: Int
    val navigateBackTo: BaseScreenTag
}