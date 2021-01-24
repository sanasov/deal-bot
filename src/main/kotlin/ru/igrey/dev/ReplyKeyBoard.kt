package ru.igrey.dev

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.igrey.dev.constant.Emoji
import ru.igrey.dev.constant.OperationType

import java.util.ArrayList

/**
 * Created by sanasov on 04.04.2017.
 */
object ReplyKeyboard {

    fun buttons(): InlineKeyboardMarkup {
        val markup = InlineKeyboardMarkup()
        val keyboard = ArrayList<List<InlineKeyboardButton>>()
        for (operationType in OperationType.values()) {
            val buttonRow = ArrayList<InlineKeyboardButton>()
            buttonRow.add(createInlineKeyboardButton(operationType))
            keyboard.add(buttonRow)
        }
        markup.keyboard = keyboard
        return markup
    }

    private fun createInlineKeyboardButton(operationType: OperationType): InlineKeyboardButton {
        val btn = InlineKeyboardButton()
        btn.text =
            (if (DealBot.currOperation == operationType) Emoji.WHITE_HEAVY_CHECK_MARK.toString() + " " else "") + operationType.label()
        btn.switchInlineQuery = "setSwitchInlineQuery"
        btn.switchInlineQueryCurrentChat = "setSwitchInlineQueryCurrentChat"
        btn.callbackData = operationType.name
        return btn
    }
}
