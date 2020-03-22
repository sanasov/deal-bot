package ru.igrey.dev.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.igrey.dev.entity.DealEntity;
import ru.igrey.dev.entity.DocumentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sanasov on 26.04.2017.
 */
public class DocumentMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DocumentEntity(
                rs.getLong("id"),
                rs.getLong("file_id"),
                rs.getLong("document_type_id"),
                rs.getString("filename"),
                rs.getLong("source_token_id"),
                rs.getBoolean("archived")
        );
    }
}