package ru.igrey.dev

import mu.KLogging
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.igrey.dev.config.DealProps.BOT_NAME
import ru.igrey.dev.config.DealProps.BOT_TOKEN
import ru.igrey.dev.config.DealProps.USERS
import ru.igrey.dev.constant.Command
import ru.igrey.dev.constant.Dictionaries.CRM_TASKS
import ru.igrey.dev.constant.Dictionaries.SERVICES
import ru.igrey.dev.constant.OperationType
import ru.igrey.dev.domain.PplDocumentType
import ru.igrey.dev.domain.UserDeals
import ru.igrey.dev.service.CasService
import ru.igrey.dev.service.DealService
import ru.igrey.dev.service.NoncreditService
import ru.igrey.dev.service.PersonService
import java.io.File
import java.net.UnknownHostException
import kotlin.math.min

/**
 * sanasov 20.03.2020
 */
class DealBot(
    defaultBotOptions: DefaultBotOptions,
    private val casService: CasService,
    private val dealService: DealService,
    private val personService: PersonService,
    private val noncreditService: NoncreditService
) : TelegramLongPollingBot(defaultBotOptions) {

    override fun onUpdateReceived(update: Update) {
        if (!isAuthorize(update)) return
        if (update.hasMessage()) {
            handleIncomingMessage(update.message)
        } else if (update.hasCallbackQuery()) {
            handleButtonClick(update.callbackQuery)
        }
    }

    private fun isAuthorize(update: Update): Boolean {
        var userId: Long? = 0L
        if (update.hasMessage()) {
            userId = update.message.chatId
        } else if (update.hasCallbackQuery()) {
            userId = update.callbackQuery.from.id.toLong()
        }
        return USERS.contains(userId)
    }

    private fun handleIncomingMessage(message: Message) {
        logger.info("Incoming message: " + message.text)
        logger.info("From user: " + message.from + "; chatId: " + message.chat.id)
        try {
            if (message.chat.isUserChat!! && message.text != null) {
                handlePrivateIncomingMessage(message)
            }
        } catch (e: UnknownHostException) {
            sendTextMessage(message.chatId, "Бот выключен")
        }
    }

    private fun handlePrivateIncomingMessage(message: Message) {
        val messageText = message.text
        when {
            Command.OPERATIONS.title == messageText -> sendButtonsMessage(message.chatId)
            Command.DICTIONARIES.title == messageText -> sendTextMessage(message.chatId, CRM_TASKS + "\n" + SERVICES)
            currOperation == OperationType.DEAL_INFO -> sendTextMessage(message.chatId, getDeal(messageText))
            currOperation == OperationType.PHONES_BY_DEAL_ID -> sendTextMessage(
                message.chatId,
                dealService.getPhonesByDealIds(parseIds(messageText))
            )
            currOperation == OperationType.PHONES_BY_CAS_ID -> sendTextMessage(
                message.chatId,
                casService.casUsers(parseIds(messageText)).toString()
            )
            currOperation == OperationType.DEALS_BY_PHONE_OR_CAS_ID -> sendTextMessage(
                message.chatId,
                userDeals(messageText)
            )
            currOperation == OperationType.PPL_IE_DOCS_BY_MONTH_AND_CAS_ID -> pplDocsForIE(message)
            currOperation == OperationType.PPL_COMPANY_DOCS_BY_MONTH_AND_CAS_ID -> pplDocsForCompany(message)
            currOperation == OperationType.PERSON_INFO_BY_FIO -> sendTextMessage(
                message.chatId,
                personService.getPersonInfo(messageText).toString()
            )
        }
    }

    private fun getDeal(messageText: String) =
        dealService.getDeal(messageText.toLong())?.toString() ?: "Сделка $messageText не найдена"

    private fun userDeals(messageText: String): String {
        val cas = casService.casUserInfo(messageText.replace("[^\\d.]".toRegex(), ""))
        return if (cas == null) "Пользователь не найден"
        else UserDeals(cas, dealService.getDeals(cas.id)).toString()
    }

    private fun pplDocsForIE(message: Message) {
        val casIdMoth = message.text.split("\\s+".toRegex())
        val casId = casIdMoth[0]
        val month = casIdMoth[1]
        var isEmpty = false
        PplDocumentType.values().forEach { type ->
            val file = noncreditService.getIEDocuments(casId, month, type)
            if (file != null) sendDocument(message.chatId, file, type.fileName(casId, month))
            else isEmpty = true
        }
        if (isEmpty) sendTextMessage(
            message.chatId,
            "Пусто. Возможно не было выплат за этот месяц."
        )
    }

    private fun pplDocsForCompany(message: Message) {
        val companyIdMoth = message.text.split("\\s+".toRegex())
        val companyId = companyIdMoth[0]
        val month = companyIdMoth[1]
        var isEmpty = false
        PplDocumentType.values().forEach { type ->
            val file = noncreditService.getCompanyDocuments(companyId, month, type)
            if (file != null) sendDocument(message.chatId, file, type.fileName(companyId, month))
            else isEmpty = true
        }
        if (isEmpty) sendTextMessage(
            message.chatId,
            "Пусто. Возможно не было выплат за этот месяц."
        )
    }

    private fun parseIds(idsAsString: String): Set<Long> =
        idsAsString.replace(',', ' ')
            .split("\\s+".toRegex())
            .map { it.trim().toLong() }
            .toSet()

    private fun sendTextMessage(chatId: Long, responseMessage: String) {
        val sendMessage = SendMessage()
        sendMessage.enableMarkdownV2(false)
        sendMessage.enableHtml(true)
        sendMessage.chatId = chatId.toString()
        sendMessage.text = responseMessage.substring(0, min(responseMessage.length, 4096))
        try {
            execute(sendMessage)
        } catch (e: TelegramApiException) {
            logger.error(e.message)
            logger.info(responseMessage)
        }
    }

    private fun sendDocument(chatId: Long, file: File, fileName: String) {
        val sendDocument = SendDocument()
        val inputFile = InputFile(file, fileName)

        sendDocument.chatId = chatId.toString()
        sendDocument.document = inputFile
        try {
            execute(sendDocument)
        } catch (e: TelegramApiException) {
            logger.error(e.message)
        }
    }

    private fun sendButtonsMessage(chatId: Long) {
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.enableMarkdown(false)
        sendMessage.chatId = chatId.toString()
        sendMessage.text = currOperation.toString()
        sendMessage.replyMarkup = ReplyKeyboard.buttons()

        try {
            execute(sendMessage)
        } catch (e: TelegramApiException) {
            logger.error(e.message)
        }
    }

    private fun editMessage(chatId: Long, messageId: Int, markup: InlineKeyboardMarkup) {
        val editMessageText = EditMessageText()
        editMessageText.chatId = chatId.toString()
        editMessageText.text = currOperation.toString()
        editMessageText.replyMarkup = markup
        editMessageText.messageId = messageId
        editMessageText.enableMarkdown(false)
        editMessageText.enableHtml(true)
        try {
            execute(editMessageText)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun handleButtonClick(query: CallbackQuery) {
        val answer = AnswerCallbackQuery()
        answer.callbackQueryId = query.id
        val operationType = OperationType.valueOf(query.data)
        logger.info("Button command: $operationType")
        currOperation = operationType
        answer.text = operationType.name
        editMessage(query.message.chatId, query.message.messageId, ReplyKeyboard.buttons())
        try {
            execute(answer)
        } catch (e: TelegramApiException) {
            logger.error("Could not send answer on button click", e)
        }
    }

    override fun getBotUsername() = BOT_NAME
    override fun getBotToken() = BOT_TOKEN

    companion object : KLogging() {
        var currOperation = OperationType.DEAL_INFO
    }
}
