package ru.igrey.dev.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by sanasov on 26.04.2017.
 */
public class CrmDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CrmDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> crmTask(Long dealId) {
        String sql = "select STRING_AGG(CONCAT(task_type_id, '(',status_id,')'), '  ' order by task_type_id)" +
                "from mastersber.crm_task where task_type_id in (740, 750, 760, 770, 780, 783) \n" +
                " and deal_id = :dealId " +
                "group by deal_id;";
        return jdbcTemplate.queryForList(sql, Collections.singletonMap("dealId", dealId), String.class);
    }

    public List<String> schedule(Long dealId) {
        String sql = "select CONCAT(date,' mikId: ',modified_by) from mastersber.crm_activity where type_id in (2, 44) and active = true and deal_id = :dealId";
        return jdbcTemplate.queryForList(sql, Collections.singletonMap("dealId", dealId), String.class);
    }
}


