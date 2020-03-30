package ru.igrey.dev.service;

import lombok.RequiredArgsConstructor;
import ru.igrey.dev.dao.CrmDao;
import ru.igrey.dev.dao.DealDao;
import ru.igrey.dev.dao.DocumentDao;
import ru.igrey.dev.dao.PXDao;
import ru.igrey.dev.domain.Deal;
import ru.igrey.dev.domain.Documents;
import ru.igrey.dev.entity.DealEntity;

import java.util.Set;

/**
 * Created by sanasov on 10.04.2017.
 */
@RequiredArgsConstructor
public class DealService {
    private final DealDao dealDao;
    private final DocumentDao documentDao;
    private final CasService casService;
    private final ServiceHubService serviceHubService;
    private final CrmDao crmDao;
    private final PXDao pxDao;

    public Deal getDeal(Long dealId) {
        DealEntity dealEntity = dealDao.findById(dealId);
        if (dealEntity == null) return null;
        return new Deal(dealEntity.getId(),
                dealEntity.getDealStatusId(),
                casService.casUser(dealEntity.getMikId()),
                dealEntity.getOfficeId(),
                dealEntity.getTypeId(),
                dealEntity.getArchived(),
                casService.casUser(dealEntity.getAuthorId()),
                crmDao.crmTask(dealId),
                new Documents(
                        documentDao.findAllNotClientDocsByDealId(dealId),
                        documentDao.countUploadedDocuments(dealId)
                ),
                serviceHubService.getServices(dealId),
                crmDao.schedule(dealId),
                pxDao.lastRequisition(dealId)
        );
    }

    public String getPhonesByCasIds(Set<Long> casIds) {
        return "dealId    casId   phone\n" + casService.casUsers(casIds);
    }

    public String getPhonesByDealIds(Set<Long> dealIds) {
        return "dealId    casId   phone\n" + dealDao.findByIds(dealIds).stream()
                .map(deal -> deal.getId() + "\t" + deal.getAuthorId() + "\t" + casService.casUser(deal.getAuthorId()).getPhone())
                .reduce((a, b) -> a + "\n" + b).orElse("");
    }

}
