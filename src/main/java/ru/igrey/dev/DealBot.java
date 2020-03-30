package ru.igrey.dev;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.igrey.dev.constant.Command;
import ru.igrey.dev.constant.OperationType;
import ru.igrey.dev.service.CasService;
import ru.igrey.dev.service.DealService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.igrey.dev.constant.Dictionaries.CRM_TASKS;
import static ru.igrey.dev.constant.Dictionaries.SERVICES;
import static ru.igrey.dev.constant.OperationType.valueOf;

/**
 * sanasov 20.03.2020
 */
@Slf4j
public class DealBot extends TelegramLongPollingBot {
    private final Long ANASOV_ID = 154090812L;
    private final List<Long> USERS = Arrays.asList(ANASOV_ID);
    private final CasService casService;
    private final DealService dealService;
    public static OperationType currentOperation = OperationType.DEAL_INFO;

    public DealBot(DefaultBotOptions defaultBotOptions, CasService casService, DealService dealService) {
        super(defaultBotOptions);
        this.casService = casService;
        this.dealService = dealService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleIncomingMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleButtonClick(update.getCallbackQuery());
        }
    }

    private void handleIncomingMessage(Message message) {
        log.info("Incoming message: " + message.getText());
        log.info("From user: " + message.getFrom() + "; chatId: " + message.getChat().getId());
        if (message.getChat().isUserChat() && message.getText() != null) {
            handlePrivateIncomingMessage(message);
        }
    }

    private void handlePrivateIncomingMessage(Message message) {
        String messageText = message.getText();
//        CasUsers result = casService.casUsers(parseIds(incomingMessageText));
        if (Command.OPERATIONS.title.equals(messageText)) {
            sendButtonsMessage(message.getChatId());
        } else if (Command.DICTIONARIES.title.equals(messageText)) {
            sendTextMessage(message.getChatId(), CRM_TASKS + "\n" + SERVICES);
        } else if (currentOperation == OperationType.DEAL_INFO) {
            sendTextMessage(message.getChatId(), dealService.getDeal(new Long(messageText)).toString());
        } else if (currentOperation == OperationType.PHONES_BY_DEAL_ID) {
            sendTextMessage(message.getChatId(), dealService.getPhonesByDealIds(parseIds(messageText)));
        } else if (currentOperation == OperationType.DEALS_BY_CAS_ID) {

        }
    }

    private Set<Long> parseIds(String idsAsString) {
        return Stream.of(idsAsString.replace(',', ' ').split("\\s"))
                .filter(StringUtils::isNotBlank)
                .map(Long::new)
                .collect(Collectors.toSet());
    }

    private void sendTextMessage(Long chatId, String responseMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(USERS.contains(chatId) ? chatId : ANASOV_ID)
                .enableMarkdown(false)
                .enableHtml(true)
                .setText(responseMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sendButtonsMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.enableMarkdown(false);
        sendMessage.setChatId(chatId);
        sendMessage.setText(currentOperation.toString());
        sendMessage.setReplyMarkup(ReplyKeyboard.buttons());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void editMessage(Long chatId, Integer messageId, InlineKeyboardMarkup markup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setText(currentOperation.toString());
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(messageId);
        editMessageText.enableMarkdown(false);
        editMessageText.enableHtml(true);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void handleButtonClick(CallbackQuery query) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(query.getId());
        OperationType operationType = valueOf(query.getData());
        log.info("Button command: " + operationType);
        currentOperation = operationType;
        answer.setText(operationType.name());
        editMessage(query.getMessage().getChatId(), query.getMessage().getMessageId(), ReplyKeyboard.buttons());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error("Could not send answer on button click", e);
        }
    }

    @Override
    public String getBotUsername() {
        return "@turnkeyDealBot";
    }

    @Override
    public String getBotToken() {
        return "1131321438:AAFoAsa1j4l5apz5H6NVI_MHGUqPC2t_X6U";
    }

}

