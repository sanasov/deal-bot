package ru.igrey.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.igrey.dev.DealBot;
import ru.igrey.dev.dao.DealDao;
import ru.igrey.dev.dao.DocumentDao;
import ru.igrey.dev.dao.JdbcTemplateFactory;
import ru.igrey.dev.service.CasService;
import ru.igrey.dev.service.DealService;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import static ru.igrey.dev.config.DealProps.*;

@Slf4j
public class BeanConfig {


    private static DealBot dealBot;
    private static CasService casService;
    private static DealService dealService;
    private static DealDao dealDao;
    private static DocumentDao documentDao;
    private static JdbcTemplate jdbcTemplate;


    public static DealBot dealBot() {
        if (dealBot == null) {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
                }
            });
            DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
            options.setProxyHost(PROXY_HOST);
            options.setProxyPort(PROXY_PORT);
            options.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
            options.setMaxThreads(10);
            dealBot = new DealBot(options, casService(), dealService());
        }
        return dealBot;
    }

    static CasService casService() {
        if (casService == null) {
            casService = new CasService();
        }
        return casService;
    }

    static DealService dealService() {
        if (dealService == null) {
            dealService = new DealService(dealDao(), documentDao(), casService());
        }
        return dealService;
    }

    static DealDao dealDao() {
        if (dealDao == null) {
            dealDao = new DealDao(jdbcTemplate());
        }
        return dealDao;
    }

    static DocumentDao documentDao() {
        if (documentDao == null) {
            documentDao = new DocumentDao(jdbcTemplate());
        }
        return documentDao;
    }


    static JdbcTemplate jdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplateFactory().projectx();
        }
        return jdbcTemplate;
    }
}
