package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.igrey.dev.dao.mapper.DealMapper;
import ru.igrey.dev.entity.DealEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by sanasov on 26.04.2017.
 */
public class DealDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public DealDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DealEntity findById(Long id) {
        String sql = "SELECT * FROM ms_deal.deal WHERE ID = :id";
        List<DealEntity> entities = jdbcTemplate.query(
                sql, Collections.singletonMap("id", id), new DealMapper());
        return entities.isEmpty() ? null : entities.get(0);
    }

    public List<DealEntity> findByAuthor(Long authorId) {
        String sql = "SELECT * FROM ms_deal.deal WHERE author_id = :authorId";
        List<DealEntity> entities = jdbcTemplate.query(
                sql, Collections.singletonMap("authorId", authorId), new DealMapper());
        return entities;
    }

    public List<DealEntity> findByIds(Set<Long> dealIds) {
        String sql = "SELECT * FROM ms_deal.deal WHERE id IN (:dealIds)";
        List<DealEntity> entities = jdbcTemplate.query(sql, Collections.singletonMap("dealIds", dealIds), new DealMapper());
        return entities;
    }
}
