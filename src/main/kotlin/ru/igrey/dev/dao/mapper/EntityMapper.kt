package ru.igrey.dev.dao.mapper

import org.springframework.jdbc.core.RowMapper
import ru.igrey.dev.entity.DealEntity
import ru.igrey.dev.entity.DealHistoryEntity
import ru.igrey.dev.entity.DocumentEntity
import ru.igrey.dev.entity.RisasRequisitionEntity
import java.sql.ResultSet
import java.sql.SQLException

class DealHistoryMapper : RowMapper {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Any {
        return DealHistoryEntity(
            rs.getLong("ID"),
            rs.getLong("DEAL_ID"),
            rs.getLong("USER_ID"),
            rs.getLong("OLD_STATUS_ID"),
            rs.getLong("NEW_STATUS_ID"),
            rs.getString("COMMENT"),
            rs.getString("EVENT"),
            rs.getLong("SOURCE_TOKEN_ID")
        )
    }
}

class DealMapper : RowMapper {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Any {
        return DealEntity(
            rs.getLong("id"),
            rs.getLong("author_id"),
            rs.getLong("deal_status_id"),
            rs.getLong("mik_id"),
            rs.getLong("office_id"),
            rs.getLong("type_id"),
            rs.getBoolean("archived"),
            rs.getTimestamp("created_time").toLocalDateTime()
        )
    }
}

class DocumentMapper : RowMapper {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Any {
        return DocumentEntity(
            rs.getLong("id"),
            rs.getLong("file_id"),
            rs.getLong("document_type_id"),
            rs.getString("filename"),
            rs.getLong("source_token_id"),
            rs.getBoolean("archived")
        )
    }
}

class RisasRequisitionMapper : RowMapper {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Any {
        return RisasRequisitionEntity(
            rs.getLong("requisition_id"),
            rs.getString("current_state")
        )
    }
}