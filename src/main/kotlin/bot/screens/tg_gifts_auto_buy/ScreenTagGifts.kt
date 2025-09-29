package bot.screens.tg_gifts_auto_buy

import bot.model.PaymentItem
import bot.screens.BaseScreenTag
import bot.screens.BaseStartTag
import bot.screens.tg_gifts_auto_buy.ScreenTag.MyPassScreenTag.*
import bot.screens.tg_gifts_auto_buy.ScreenTag.StartScreenTag.*

sealed class ScreenTag(
    override val tag: String,
    override val callbackId: String
): BaseScreenTag {
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
    BaseStartTag,
    MyPass,
    SetTrackingAndAutoPurchase,
    Faq,
    AutoTracking(),
    AutoPurchase(),
    BackToMenu
)