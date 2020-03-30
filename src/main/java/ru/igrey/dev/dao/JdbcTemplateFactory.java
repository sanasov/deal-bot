package ru.igrey.dev.dao;

import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static ru.igrey.dev.config.DealProps.*;

public class JdbcTemplateFactory {


    public NamedParameterJdbcTemplate projectx() {
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setUser(USER);
        dataSource.setPassword(PX_PASSWORD);
        dataSource.setServerName(PX_HOST);
        dataSource.setPortNumber(PX_PORT);
        dataSource.setDatabaseName(PX_DB);
        return new NamedParameterJdbcTemplate(dataSource);
    }

    public NamedParameterJdbcTemplate mastersber() {
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setUser(USER);
        dataSource.setPassword(MS_PASSWORD);
        dataSource.setServerName(HOST);
        dataSource.setPortNumber(MS_PORT);
        dataSource.setDatabaseName(MS_DB);
        return new NamedParameterJdbcTemplate(dataSource);
    }


}
