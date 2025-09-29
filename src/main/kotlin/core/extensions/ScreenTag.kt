package core.extensions

import bot.data.KeyboardButton
import bot.screens.BaseScreenTag

fun BaseScreenTag.asKeyboardItem() = listOf(KeyboardButton(text = tag, callbackData = callbackId))