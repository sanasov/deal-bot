package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.igrey.dev.dao.mapper.DealMapper;
import ru.igrey.dev.dao.mapper.DocumentMapper;
import ru.igrey.dev.entity.DealEntity;
import ru.igrey.dev.entity.DocumentEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by sanasov on 26.04.2017.
 */
public class CrmDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CrmDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> crmTask(Long dealId) {
        String sql = "select STRING_AGG(task_type_id || '(' || status_id || ')', '  ' order by task_type_id)" +
                "from crm_task where task_type_id in (740, 750, 760, 770, 780, 783) \n" +
                " and deal_id = :dealId " +
                "group by deal_id;";
        return jdbcTemplate.queryForList(sql, Collections.singletonMap("dealId", dealId), String.class);
    }

    public List<String> schedule(Long dealId) {
        String sql = "select date || ' mikId: ' || modified_by from crm_activity where type_id in (2, 44) and active = true and deal_id = :dealId";
        return jdbcTemplate.queryForList(sql, Collections.singletonMap("dealId", dealId), String.class);
    }

    public List<DealEntity> findByIds(Set<Long> dealIds) {
        String sql = "SELECT * FROM ms_deal.deal WHERE id IN (:dealIds)";
        List<DealEntity> entities = jdbcTemplate.query(sql, Collections.singletonMap("dealIds", dealIds), new DealMapper());
        return entities;
    }
}


