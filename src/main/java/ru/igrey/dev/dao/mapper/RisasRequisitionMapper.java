package ru.igrey.dev.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.igrey.dev.entity.DealEntity;
import ru.igrey.dev.entity.RisasRequisitionEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sanasov on 26.04.2017.
 */
public class RisasRequisitionMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RisasRequisitionEntity(
                rs.getLong("requisition_id"),
                rs.getString("current_state")
        );
    }
}