package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.igrey.dev.dao.mapper.DealMapper;
import ru.igrey.dev.dao.mapper.DocumentMapper;
import ru.igrey.dev.entity.DealEntity;
import ru.igrey.dev.entity.DocumentEntity;

import java.util.List;

/**
 * Created by sanasov on 26.04.2017.
 */
public class DocumentDao {
    private JdbcTemplate jdbcTemplate;

    public DocumentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DocumentEntity> findAllNotClientDocsByDealId(Long dealId) {
        String sql = "SELECT * FROM ms_deal.deal_document WHERE DEAL_ID = ? AND DOCUMENT_TYPE_ID <> 20750";
        return jdbcTemplate.query(sql, new Object[]{dealId}, new DocumentMapper());
    }

    public Integer countUploadedDocuments(Long dealId) {
        String sql = "SELECT count(*) FROM ms_deal.deal_document WHERE DEAL_ID = ? AND DOCUMENT_TYPE_ID = 20750 AND modified_by <> 1";
        return jdbcTemplate.queryForInt(sql, new Object[]{dealId});
    }

    public List<DocumentEntity> findByDealId(Long dealId) {
        String sql = "SELECT * FROM ms_deal.deal_document WHERE DEAL_ID = ?";
        return jdbcTemplate.query(sql, new Object[]{dealId}, new DocumentMapper());
    }

}

//        20750
//        25206
//        25330
//        25331
//        25332
//        25333
//        25334

