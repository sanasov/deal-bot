package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.igrey.dev.dao.mapper.DocumentMapper;
import ru.igrey.dev.entity.DocumentEntity;

import java.util.List;

/**
 * Created by sanasov on 26.04.2017.
 */
public class CrmTaskDao {
    private JdbcTemplate jdbcTemplate;

    public CrmTaskDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String crmTask(Long dealId) {
        String sql = "select STRING_AGG(task_type_id || '(' || status_id || ')', '  ' order by task_type_id)" +
                "from crm_task where task_type_id in (740, 750, 760, 770, 780, 783) \n" +
                " and deal_id = ? " +
                "group by deal_id;";
        return (String) jdbcTemplate.queryForObject(sql, new Object[]{dealId}, String.class);
    }
}

//        20750
//        25206
//        25330
//        25331
//        25332
//        25333
//        25334

