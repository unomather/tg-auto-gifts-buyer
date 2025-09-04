package bot.screens

import bot.screens.ScreenTag.MyPassScreenTag.AutoPurchase
import bot.screens.ScreenTag.MyPassScreenTag.AutoTracking
import bot.screens.ScreenTag.MyPassScreenTag.BackToMenu
import bot.screens.ScreenTag.StartScreenTag.Faq
import bot.screens.ScreenTag.StartScreenTag.MyPass
import bot.screens.ScreenTag.StartScreenTag.SetTrackingAndAutoPurchase
import bot.screens.ScreenTag.StartTag

sealed class ScreenTag(open val tag: String) {
    /** START **/
    data object StartTag: ScreenTag("/start")

    /** START SCREEN **/
    sealed class StartScreenTag(override val tag: String): ScreenTag(tag) {
        data object MyPass: StartScreenTag("Мой пропуск")
        data object SetTrackingAndAutoPurchase: StartScreenTag("Настроить отслеживание и автозакуп")
        data object Faq: StartScreenTag("Вопросы и ответы")
    }

    /** START SCREEN **/
    sealed class MyPassScreenTag(override val tag: String): ScreenTag(tag) {
        data object AutoTracking: MyPassScreenTag("Отслеживание - 499 ⭐")
        data object AutoPurchase: MyPassScreenTag("Автозакуп - 1499 ⭐")
        data object BackToMenu: MyPassScreenTag("Назад в меню")
    }
}

val allScreenTags = listOf(
    StartTag,
    MyPass,
    SetTrackingAndAutoPurchase,
    Faq,
    AutoTracking,
    AutoPurchase,
    BackToMenu
)