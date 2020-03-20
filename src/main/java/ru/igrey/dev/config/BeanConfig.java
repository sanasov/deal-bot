package ru.igrey.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.igrey.dev.DealBot;
import ru.igrey.dev.service.CasService;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

@Slf4j
public class BeanConfig {
    private static String PROXY_HOST = "80.211.231.220" /* proxy host */;
    private static Integer PROXY_PORT = 1080 /* proxy port */;
    private static String PROXY_USER = "" /* proxy port */;
    private static String PROXY_PASSWORD = "" /* proxy port */;

    private static DealBot dealBot;
    private static CasService casService;


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
            dealBot = new DealBot(options, casService());
        }
        return dealBot;
    }

    public static CasService casService() {
        if (casService == null) {
            casService = new CasService();
        }
        return casService;
    }
}
