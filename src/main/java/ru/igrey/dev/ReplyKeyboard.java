package ru.igrey.dev;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.igrey.dev.constant.Emoji;
import ru.igrey.dev.constant.OperationType;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sanasov on 04.04.2017.
 */
public class ReplyKeyboard {

    public static InlineKeyboardMarkup buttons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (OperationType operationType : OperationType.values()) {
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            buttonRow.add(createInlineKeyboardButton(operationType));
            keyboard.add(buttonRow);
        }
        markup.setKeyboard(keyboard);
        return markup;
    }

    private static InlineKeyboardButton createInlineKeyboardButton(OperationType operationType) {
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText((DealBot.currentOperation == operationType ? Emoji.WHITE_HEAVY_CHECK_MARK + " " : "") + operationType.label());
        btn.setSwitchInlineQuery("setSwitchInlineQuery");
        btn.setSwitchInlineQueryCurrentChat("setSwitchInlineQueryCurrentChat");
        btn.setCallbackData(operationType.name());
        return btn;
    }
}
