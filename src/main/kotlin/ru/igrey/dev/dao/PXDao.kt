package ru.igrey.dev.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import ru.igrey.dev.dao.mapper.RisasRequisitionMapper
import ru.igrey.dev.entity.RisasRequisitionEntity

import java.util.Collections

class PXDao(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun lastRequisition(dealId: Long?): List<RisasRequisitionEntity> {
        val sql = "SELECT reqs.id AS requisition_id,transitions.to_state AS current_state, es.title " +
                "FROM risas_production.requisitions AS reqs\n" +
                "INNER JOIN risas_production.entry_sources AS es ON reqs.entry_source_id=es.id\n" +
                "INNER JOIN risas_production.requisition_transitions AS transitions ON most_recent=true AND transitions.requisition_id=reqs.id\n" +
                "WHERE(es.title='СПК пакет 1'OR es.title='СПК пакет 2') AND deal_id=:dealId AND deleted=FALSE;"
        return jdbcTemplate.query(
            sql,
            Collections.singletonMap("dealId", dealId),
            RisasRequisitionMapper()
        ) as List<RisasRequisitionEntity>
    }
}

//
//
//        SELECT reqs.id,transitions.to_state AS current_state,entry_sources.title,deal_id
//        FROM risas_production.requisitions AS reqs
//        INNER JOIN risas_production.entry_sources AS es ON reqs.entry_source_id=es.id
//        INNER JOIN risas_production.requisition_transitions AS transitions ON most_recent=true AND transitions.requisition_id=reqs.id
//        WHERE(es.title='СПК пакет 1'OR es.title='СПК пакет 2') AND deal_id=? AND deleted=FALSE;