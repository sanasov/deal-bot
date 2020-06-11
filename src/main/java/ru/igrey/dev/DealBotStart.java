package ru.igrey.dev;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.igrey.dev.config.BeanConfig;

/**
 * Created by sanasov on 01.04.2017.
 */
@Slf4j
public class DealBotStart {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(BeanConfig.dealBot());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
