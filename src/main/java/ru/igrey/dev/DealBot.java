package ru.igrey.dev;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.igrey.dev.domain.CasUsers;
import ru.igrey.dev.service.CasService;

import java.lang.reflect.Array;
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
    private Long ANASOV_ID = 154090812L;
    private List<Long> USERS = Arrays.asList(ANASOV_ID);
    private CasService casService;

    public DealBot(DefaultBotOptions defaultBotOptions, CasService casService) {
        super(defaultBotOptions);
        this.casService = casService;
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

    private void handlePrivateIncomingMessage(Message incomingMessage) {
        Long chatId = incomingMessage.getChatId();
        String incomingMessageText = incomingMessage.getText();
        CasUsers casUsers = casService.casUsers(parseIds(incomingMessageText));
        sendTextMessage(chatId, casUsers.toString());
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
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

}

