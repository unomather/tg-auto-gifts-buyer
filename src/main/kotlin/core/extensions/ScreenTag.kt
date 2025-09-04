package core.extensions

import bot.data.KeyboardButton
import bot.screens.ScreenTag

fun ScreenTag.asKeyboardItem() = listOf(KeyboardButton(tag))