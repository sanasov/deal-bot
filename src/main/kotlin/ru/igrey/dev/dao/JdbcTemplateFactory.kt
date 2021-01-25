package ru.igrey.dev.dao

import org.postgresql.jdbc2.optional.SimpleDataSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import ru.igrey.dev.config.DealProps.HOST
import ru.igrey.dev.config.DealProps.MS_DB
import ru.igrey.dev.config.DealProps.MS_DEAL_DB
import ru.igrey.dev.config.DealProps.MS_DEAL_HOST
import ru.igrey.dev.config.DealProps.MS_DEAL_PASSWORD
import ru.igrey.dev.config.DealProps.MS_DEAL_PORT
import ru.igrey.dev.config.DealProps.MS_PASSWORD
import ru.igrey.dev.config.DealProps.MS_PORT
import ru.igrey.dev.config.DealProps.PX_DB
import ru.igrey.dev.config.DealProps.PX_HOST
import ru.igrey.dev.config.DealProps.PX_PASSWORD
import ru.igrey.dev.config.DealProps.PX_PORT
import ru.igrey.dev.config.DealProps.USER

class JdbcTemplateFactory {

    fun projectx(): NamedParameterJdbcTemplate {
        val dataSource = SimpleDataSource()
        dataSource.user = USER
        dataSource.password = PX_PASSWORD
        dataSource.serverName = PX_HOST
        dataSource.portNumber = PX_PORT
        dataSource.databaseName = PX_DB
        return NamedParameterJdbcTemplate(dataSource)
    }

    fun mastersber(): NamedParameterJdbcTemplate {
        val dataSource = SimpleDataSource()
        dataSource.user = USER
        dataSource.password = MS_PASSWORD
        dataSource.serverName = HOST
        dataSource.portNumber = MS_PORT
        dataSource.databaseName = MS_DB
        return NamedParameterJdbcTemplate(dataSource)
    }

    fun jdbcTemplateMSDeal(): NamedParameterJdbcTemplate {
        val dataSource = SimpleDataSource()
        dataSource.user = USER
        dataSource.password = MS_DEAL_PASSWORD
        dataSource.serverName = MS_DEAL_HOST
        dataSource.portNumber = MS_DEAL_PORT
        dataSource.databaseName = MS_DEAL_DB
        return NamedParameterJdbcTemplate(dataSource)
    }
}
