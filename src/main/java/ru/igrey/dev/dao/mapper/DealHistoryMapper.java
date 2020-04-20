package ru.igrey.dev.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.igrey.dev.entity.DealHistoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sanasov on 26.04.2017.
 */
public class DealHistoryMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DealHistoryEntity(
                rs.getLong("ID"),
                rs.getLong("DEAL_ID"),
                rs.getLong("USER_ID"),
                rs.getLong("OLD_STATUS_ID"),
                rs.getLong("NEW_STATUS_ID"),
                rs.getString("COMMENT"),
                rs.getString("EVENT"),
                rs.getLong("SOURCE_TOKEN_ID")
        );
    }
}