package ru.igrey.dev.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import ru.igrey.dev.dao.mapper.DocumentMapper
import ru.igrey.dev.entity.DocumentEntity
import java.util.Collections

/**
 * Created by sanasov on 26.04.2017.
 */
class DocumentDao(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun findAllNotClientDocsByDealId(dealId: Long?): List<DocumentEntity> {
        val sql = "SELECT * FROM ms_deal.deal_document WHERE DEAL_ID = :dealId AND DOCUMENT_TYPE_ID <> 20750"
        return jdbcTemplate.query(
            sql,
            Collections.singletonMap("dealId", dealId),
            DocumentMapper()
        ) as List<DocumentEntity>
    }

    fun countUploadedDocuments(dealId: Long?): Int? {
        val sql =
            "SELECT count(*) FROM ms_deal.deal_document WHERE DEAL_ID = :dealId AND DOCUMENT_TYPE_ID = 20750 AND modified_by <> 1"
        return jdbcTemplate.queryForInt(sql, Collections.singletonMap("dealId", dealId))
    }

    fun findByDealId(dealId: Long?): List<DocumentEntity> {
        val sql = "SELECT * FROM ms_deal.deal_document WHERE DEAL_ID = :dealId"
        return jdbcTemplate.query(
            sql,
            Collections.singletonMap("dealId", dealId),
            DocumentMapper()
        ) as List<DocumentEntity>
    }
}

//        20750
//        25206
//        25330
//        25331
//        25332
//        25333
//        25334

