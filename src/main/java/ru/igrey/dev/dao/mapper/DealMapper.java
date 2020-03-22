package ru.igrey.dev.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.igrey.dev.entity.DealEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sanasov on 26.04.2017.
 */
public class DealMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DealEntity(
                rs.getLong("id"),
                rs.getLong("author_id"),
                rs.getLong("deal_status_id"),
                rs.getLong("mik_id"),
                rs.getLong("office_id"),
                rs.getLong("type_id"),
                rs.getBoolean("archived")
        );
    }
}