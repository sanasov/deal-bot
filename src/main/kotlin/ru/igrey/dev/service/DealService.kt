package ru.igrey.dev.service

import ru.igrey.dev.dao.CrmDao
import ru.igrey.dev.dao.DealDao
import ru.igrey.dev.dao.DocumentDao
import ru.igrey.dev.dao.PXDao
import ru.igrey.dev.domain.Deal
import ru.igrey.dev.domain.Documents
import ru.igrey.dev.entity.DealEntity

/**
 * Created by sanasov on 10.04.2017.
 */
class DealService(
    private val dealDao: DealDao,
    private val documentDao: DocumentDao,
    private val casService: CasService,
    private val serviceHubService: ServiceHubService,
    private val crmDao: CrmDao,
    private val pxDao: PXDao
) {

    fun getDeals(authorId: Long?): List<DealEntity> {
        return dealDao.findByAuthor(authorId)
    }

    fun getDeal(dealId: Long?): Deal? {
        val deal = dealDao.findById(dealId)
        val crmTasks = crmDao.crmTask(dealId)
        if (deal == null) return null
        return with(deal) {
            Deal(
                id,
                dealStatusId,
                casService.casUser(mikId),
                officeId,
                typeId,
                archived,
                casService.casUser(authorId),
                if (crmTasks.isEmpty()) "" else crmTasks[0],
                Documents(
                    documentDao.findAllNotClientDocsByDealId(dealId),
                    documentDao.countUploadedDocuments(dealId)!!
                ),
                serviceHubService.getServices(dealId!!),
                crmDao.schedule(dealId),
                pxDao.lastRequisition(dealId),
                dealDao.findLastHistoryById(dealId),
                createdTime
            )
        }
    }

    fun getPhonesByDealIds(dealIds: Set<Long>): String {
        return dealDao!!.findByIds(dealIds).stream()
            .map { (id, authorId) -> id.toString() + "\t" + casService!!.casUser(authorId)!!.toString() }
            .reduce { a, b -> a + "\n" + b }.orElse("")
    }
}
