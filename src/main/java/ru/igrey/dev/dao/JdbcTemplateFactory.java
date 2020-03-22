package ru.igrey.dev.dao;

import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import static ru.igrey.dev.config.DealProps.*;

public class JdbcTemplateFactory {

    private static JdbcTemplate jdbcTemplate;

    public JdbcTemplate projectx() {
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setServerName(HOST);
        dataSource.setPortNumber(PORT);
        dataSource.setDatabaseName(DB);
        return new JdbcTemplate(dataSource);
    }


}
