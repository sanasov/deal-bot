package ru.igrey.dev.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.igrey.dev.dao.mapper.DealHistoryMapper;
import ru.igrey.dev.dao.mapper.DealMapper;
import ru.igrey.dev.entity.DealEntity;
import ru.igrey.dev.entity.DealHistoryEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DealDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public DealDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DealEntity findById(Long id) {
        String sql = "SELECT * FROM deal WHERE ID = :id";
        List<DealEntity> entities = jdbcTemplate.query(
                sql, Collections.singletonMap("id", id), new DealMapper());
        return entities.isEmpty() ? null : entities.get(0);
    }

    public List<DealHistoryEntity> findLastHistoryById(Long dealId) {
        String sql = "SELECT * FROM deal_history WHERE DEAL_ID = :dealId ORDER BY ID DESC LIMIT 1";
        List<DealHistoryEntity> entities = jdbcTemplate.query(
                sql, Collections.singletonMap("dealId", dealId), new DealHistoryMapper());
        return entities;
    }

    public List<DealEntity> findByAuthor(Long authorId) {
        String sql = "SELECT * FROM deal WHERE author_id = :authorId";
        List<DealEntity> entities = jdbcTemplate.query(
                sql, Collections.singletonMap("authorId", authorId), new DealMapper());
        return entities;
    }

    public List<DealEntity> findByIds(Set<Long> dealIds) {
        String sql = "SELECT * FROM deal WHERE id IN (:dealIds)";
        List<DealEntity> entities = jdbcTemplate.query(sql, Collections.singletonMap("dealIds", dealIds), new DealMapper());
        return entities;
    }
}
