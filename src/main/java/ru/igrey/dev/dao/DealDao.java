package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.igrey.dev.dao.mapper.DealMapper;
import ru.igrey.dev.entity.DealEntity;

import java.util.List;

/**
 * Created by sanasov on 26.04.2017.
 */
public class DealDao {
    private JdbcTemplate jdbcTemplate;

    public DealDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DealEntity findById(Long id) {
        String sql = "SELECT * FROM ms_deal.deal WHERE ID = ?";
        List<DealEntity> entities = jdbcTemplate.query(
                sql, new Object[]{id}, new DealMapper());
        return entities.isEmpty() ? null : entities.get(0);
    }

    public List<DealEntity> findByAuthor(Long authorId) {
        String sql = "SELECT * FROM ms_deal.deal WHERE author_id = ?";
        List<DealEntity> entities = jdbcTemplate.query(
                sql, new Object[]{authorId}, new DealMapper());
        return entities;
    }
}
