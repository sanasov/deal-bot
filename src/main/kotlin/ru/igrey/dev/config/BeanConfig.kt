package ru.igrey.dev.config

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.telegram.telegrambots.bots.DefaultBotOptions
import ru.igrey.dev.DealBot
import ru.igrey.dev.config.DealProps.PROXY_HOST
import ru.igrey.dev.config.DealProps.PROXY_PASSWORD
import ru.igrey.dev.config.DealProps.PROXY_PORT
import ru.igrey.dev.config.DealProps.PROXY_USER
import ru.igrey.dev.dao.CrmDao
import ru.igrey.dev.dao.DealDao
import ru.igrey.dev.dao.DocumentDao
import ru.igrey.dev.dao.JdbcTemplateFactory
import ru.igrey.dev.dao.PXDao
import ru.igrey.dev.service.CasService
import ru.igrey.dev.service.DealService
import ru.igrey.dev.service.NoncreditService
import ru.igrey.dev.service.PersonService
import ru.igrey.dev.service.ServiceHubService
import java.net.Authenticator
import java.net.PasswordAuthentication

object BeanConfig {
    private var dealBot: DealBot? = null
    private var casService: CasService? = null
    private var personService: PersonService? = null
    private var noncreditService: NoncreditService? = null
    private var serviceHubService: ServiceHubService? = null
    private var dealService: DealService? = null
    private var dealDao: DealDao? = null
    private var documentDao: DocumentDao? = null
    private var crmDao: CrmDao? = null
    private var pxDao: PXDao? = null
    private var jdbcTemplatePX: NamedParameterJdbcTemplate? = null
    private var jdbcTemplateMS: NamedParameterJdbcTemplate? = null
    private var jdbcTemplateMSDeal: NamedParameterJdbcTemplate? = null

    fun dealBot(): DealBot {
        Authenticator.setDefault(object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray())
            }
        })
        val options = DefaultBotOptions()
        //            options.setProxyPort(PROXY_PORT);
        options.maxThreads = 10
        return DealBot(options, casService(), dealService(), personService(), noncreditService())
    }

    private fun casService(): CasService {
        if (casService == null) {
            casService = CasService()
        }
        return casService!!
    }

    private fun noncreditService(): NoncreditService {
        if (noncreditService == null) {
            noncreditService = NoncreditService()
        }
        return noncreditService!!
    }

    private fun serviceHubService() = ServiceHubService()

    private fun dealService() =
        DealService(dealDao(), documentDao(), casService(), serviceHubService(), crmTaskDao(), pxDao())

    private fun dealDao(): DealDao {
        if (dealDao == null) {
            dealDao = DealDao(jdbcTemplatePX())
        }
        return dealDao!!
    }

    private fun personService(): PersonService {
        if (personService == null) {
            personService = PersonService(casService())
        }
        return personService!!
    }

    private fun documentDao(): DocumentDao {
        if (documentDao == null) {
            documentDao = DocumentDao(jdbcTemplatePX())
        }
        return documentDao!!
    }

    private fun crmTaskDao(): CrmDao {
        if (crmDao == null) {
            crmDao = CrmDao(jdbcTemplatePX())
        }
        return crmDao!!
    }

    private fun pxDao(): PXDao {
        if (pxDao == null) {
            pxDao = PXDao(jdbcTemplatePX())
        }
        return pxDao!!
    }

    private fun jdbcTemplatePX(): NamedParameterJdbcTemplate {
        if (jdbcTemplatePX == null) {
            jdbcTemplatePX = JdbcTemplateFactory().projectx()
        }
        return jdbcTemplatePX!!
    }

    private fun jdbcTemplateMS(): NamedParameterJdbcTemplate {
        if (jdbcTemplateMS == null) {
            jdbcTemplateMS = JdbcTemplateFactory().mastersber()
        }
        return jdbcTemplateMS!!
    }

    private fun jdbcTemplateMSDeal(): NamedParameterJdbcTemplate {
        if (jdbcTemplateMSDeal == null) {
            jdbcTemplateMSDeal = JdbcTemplateFactory().jdbcTemplateMSDeal()
        }
        return jdbcTemplateMSDeal!!
    }
}
