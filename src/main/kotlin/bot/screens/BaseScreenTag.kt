package bot.screens

interface BaseScreenTag {
    val tag: String
    val callbackId: String
}

data object BaseStartTag: BaseScreenTag {
    override val tag: String = "/start"
    override val callbackId: String = "start"
}