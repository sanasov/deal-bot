package ru.igrey.dev.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

import java.util.Collections

/**
 * Created by sanasov on 26.04.2017.
 */
class CrmDao(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun crmTask(dealId: Long?): List<String> {
        val sql = "select STRING_AGG(CONCAT(task_type_id, '(',status_id,')'), '  ' order by task_type_id)" +
                "from mastersber.crm_task where task_type_id in (740, 750, 760, 770, 780, 783) \n" +
                " and deal_id = :dealId " +
                "group by deal_id;"
        return jdbcTemplate.queryForList(
            sql,
            Collections.singletonMap("dealId", dealId),
            String::class.java
        ) as List<String>
    }

    fun schedule(dealId: Long?): List<String> {
        val sql =
            "select CONCAT(date,' mikId: ',modified_by) from mastersber.crm_activity where type_id in (2, 44) and active = true and deal_id = :dealId"
        return jdbcTemplate.queryForList(
            sql,
            Collections.singletonMap("dealId", dealId),
            String::class.java
        ) as List<String>
    }
}
