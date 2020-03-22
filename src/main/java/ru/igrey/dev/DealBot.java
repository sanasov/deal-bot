package ru.igrey.dev;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.igrey.dev.service.CasService;
import ru.igrey.dev.service.DealService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * sanasov 20.03.2020
 */
@Slf4j
public class DealBot extends TelegramLongPollingBot {
    private final Long ANASOV_ID = 154090812L;
    private final List<Long> USERS = Arrays.asList(ANASOV_ID);
    private final CasService casService;
    private final DealService dealService;

    public DealBot(DefaultBotOptions defaultBotOptions, CasService casService, DealService dealService) {
        super(defaultBotOptions);
        this.casService = casService;
        this.dealService = dealService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleIncomingMessage(update.getMessage());
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
        sendTextMessage(message.getChatId(), dealService.getDeal(new Long(messageText)).toString());
    }

    private Set<Long> parseIds(String idsAsString) {
        return Stream.of(idsAsString.split("\\s"))
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

    @Override
    public String getBotUsername() {
        return "@turnkeyDealBot";
    }

    @Override
    public String getBotToken() {
        return "1131321438:AAFoAsa1j4l5apz5H6NVI_MHGUqPC2t_X6U";
    }

}

