package ru.igrey.dev.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import ru.igrey.dev.dao.mapper.DealHistoryMapper
import ru.igrey.dev.dao.mapper.DealMapper
import ru.igrey.dev.entity.DealEntity
import ru.igrey.dev.entity.DealHistoryEntity
import java.util.Collections

class DealDao(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun findById(id: Long?): DealEntity? {
        val sql = "SELECT * FROM ms_deal.deal WHERE ID = :id"
        val entities = jdbcTemplate.query(
            sql, Collections.singletonMap("id", id), DealMapper()
        )
        return (if (entities.isEmpty()) null else entities[0]) as DealEntity?
    }

    fun findLastHistoryById(dealId: Long?): List<DealHistoryEntity> {
        val sql = "SELECT * FROM ms_deal.deal_history WHERE DEAL_ID = :dealId ORDER BY ID DESC LIMIT 1"
        return jdbcTemplate.query(
            sql, Collections.singletonMap("dealId", dealId), DealHistoryMapper()
        ) as List<DealHistoryEntity>
    }

    fun findByAuthor(authorId: Long?): List<DealEntity> {
        val sql = "SELECT * FROM ms_deal.deal WHERE author_id = :authorId"
        return jdbcTemplate.query(
            sql, Collections.singletonMap("authorId", authorId), DealMapper()
        ) as List<DealEntity>
    }

    fun findByIds(dealIds: Set<Long>): List<DealEntity> {
        val sql = "SELECT * FROM ms_deal.deal WHERE id IN (:dealIds)"
        return jdbcTemplate.query(sql, Collections.singletonMap("dealIds", dealIds),
            DealMapper()
        ) as List<DealEntity>
    }
}
