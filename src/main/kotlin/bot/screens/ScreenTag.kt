package bot.screens

import bot.model.PaymentItem
import bot.screens.ScreenTag.MyPassScreenTag.AutoPurchase
import bot.screens.ScreenTag.MyPassScreenTag.AutoTracking
import bot.screens.ScreenTag.MyPassScreenTag.BackToMenu
import bot.screens.ScreenTag.StartScreenTag.Faq
import bot.screens.ScreenTag.StartScreenTag.MyPass
import bot.screens.ScreenTag.StartScreenTag.SetTrackingAndAutoPurchase
import bot.screens.ScreenTag.StartTag

sealed class ScreenTag(
    open val tag: String,
    open val callbackId: String
) {
    /** START **/
    data object StartTag: ScreenTag(
        tag = "/start",
        callbackId = "start"
    )

    /** START SCREEN **/
    sealed class StartScreenTag(
        override val tag: String,
        override val callbackId: String
    ): ScreenTag(
        tag = tag,
        callbackId = callbackId
    ) {
        data object MyPass: StartScreenTag(
            tag = "Мой пропуск",
            callbackId = "my_pass"
        )
        data object SetTrackingAndAutoPurchase: StartScreenTag(
            tag = "Настроить отслеживание и автозакуп",
            callbackId = "auto_track_buy_settings"
        )
        data object Faq: StartScreenTag(
            tag = "Вопросы и ответы",
            callbackId = "fqa"
        )
    }

    /** START SCREEN **/
    sealed class MyPassScreenTag(
        override val tag: String,
        override val callbackId: String
    ): ScreenTag(
        tag = tag,
        callbackId = callbackId
    ) {
        data class AutoTracking(
            override val tag: String = "Отслеживание - 1 ⭐",
            override val callbackId: String = "auto_track",
            override val title: String = "Автоотслеживание",
            override val description: String = "Доступ к автоотслеживанию подарков",
            override val price: Int = 1,
            override val navigateBackTo: ScreenTag = MyPass
        ): PaymentItem, MyPassScreenTag(
            tag = tag,
            callbackId = callbackId
        )
        data class AutoPurchase(
            override val tag: String = "Автозакуп - 2 ⭐",
            override val callbackId: String = "auto_buy",
            override val title: String = "Автозакуп",
            override val description: String = "Доступ к автозакупу подарков",
            override val price: Int = 2,
            override val navigateBackTo: ScreenTag = MyPass
        ): PaymentItem, MyPassScreenTag(
            tag = tag,
            callbackId = callbackId
        )
        data object BackToMenu: MyPassScreenTag(
            tag = "Назад в меню",
            callbackId = "back_from_my_pass"
        )
    }
}

val allScreenTags = listOf(
    StartTag,
    MyPass,
    SetTrackingAndAutoPurchase,
    Faq,
    AutoTracking(),
    AutoPurchase(),
    BackToMenu
)