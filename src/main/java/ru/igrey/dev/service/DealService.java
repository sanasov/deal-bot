package ru.igrey.dev.service;

import lombok.RequiredArgsConstructor;
import ru.igrey.dev.dao.DealDao;
import ru.igrey.dev.dao.DocumentDao;
import ru.igrey.dev.domain.Deal;
import ru.igrey.dev.domain.Documents;
import ru.igrey.dev.entity.DealEntity;

/**
 * Created by sanasov on 10.04.2017.
 */
@RequiredArgsConstructor
public class DealService {
    private final DealDao dealDao;
    private final DocumentDao documentDao;
    private final CasService casService;

    public Deal getDeal(Long dealId) {
        DealEntity dealEntity = dealDao.findById(dealId);
        return new Deal(dealEntity.getId(),
                dealEntity.getDealStatusId(),
                dealEntity.getMikId(),
                dealEntity.getOfficeId(),
                dealEntity.getTypeId(),
                dealEntity.getArchived(),
                casService.casUser(dealEntity.getAuthorId()),
                new Documents(
                        documentDao.findAllNotClientDocsByDealId(dealId),
                        documentDao.countUploadedDocuments(dealId)
                )
        );
    }
}
