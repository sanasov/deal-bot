package ru.igrey.dev

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.igrey.dev.config.BeanConfig

/**
 * Created by sanasov on 01.04.2017.
 */

fun main(args: Array<String>) {
    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        botsApi.registerBot(BeanConfig.dealBot())
}