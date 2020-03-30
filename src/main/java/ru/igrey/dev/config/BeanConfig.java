package ru.igrey.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.igrey.dev.DealBot;
import ru.igrey.dev.dao.*;
import ru.igrey.dev.service.CasService;
import ru.igrey.dev.service.DealService;
import ru.igrey.dev.service.ServiceHubService;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import static ru.igrey.dev.config.DealProps.*;

@Slf4j
public class BeanConfig {
    private static DealBot dealBot;
    private static CasService casService;
    private static ServiceHubService serviceHubService;
    private static DealService dealService;
    private static DealDao dealDao;
    private static DocumentDao documentDao;
    private static CrmDao crmDao;
    private static PXDao pxDao;
    private static NamedParameterJdbcTemplate jdbcTemplatePX;
    private static NamedParameterJdbcTemplate jdbcTemplateMS;


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

    static ServiceHubService serviceHubService() {
        if (serviceHubService == null) {
            serviceHubService = new ServiceHubService();
        }
        return serviceHubService;
    }

    static DealService dealService() {
        if (dealService == null) {
            dealService = new DealService(dealDao(), documentDao(), casService(), serviceHubService(), crmTaskDao(), pxDao());
        }
        return dealService;
    }

    static DealDao dealDao() {
        if (dealDao == null) {
            dealDao = new DealDao(jdbcTemplatePX());
        }
        return dealDao;
    }

    static DocumentDao documentDao() {
        if (documentDao == null) {
            documentDao = new DocumentDao(jdbcTemplatePX());
        }
        return documentDao;
    }

    static CrmDao crmTaskDao() {
        if (crmDao == null) {
            crmDao = new CrmDao(jdbcTemplateMS());
        }
        return crmDao;
    }

    static PXDao pxDao() {
        if (pxDao == null) {
            pxDao = new PXDao(jdbcTemplatePX());
        }
        return pxDao;
    }


    static NamedParameterJdbcTemplate jdbcTemplatePX() {
        if (jdbcTemplatePX == null) {
            jdbcTemplatePX = new JdbcTemplateFactory().projectx();
        }
        return jdbcTemplatePX;
    }

    static NamedParameterJdbcTemplate jdbcTemplateMS() {
        if (jdbcTemplateMS == null) {
            jdbcTemplateMS = new JdbcTemplateFactory().mastersber();
        }
        return jdbcTemplateMS;
    }
}
