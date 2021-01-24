package ru.igrey.dev.domain

import org.apache.commons.lang3.StringUtils
import ru.igrey.dev.entity.DealEntity

data class UserDeals(
    val cas: Cas,
    val deals: List<DealEntity>
) {

    override fun toString() =
        """$cas
${StringUtils.repeat("-", 30)}
СПК:   ${dealsAsString(55562L)}
УЗ:    ${dealsAsString(55561L)}
ИПОТЕКА:   ${dealsAsString(55550L)}"""

    private fun dealsAsString(typeId: Long?): String =
        deals.filter { it.typeId == typeId }
            .map { "${it.id} (${it.dealStatusId}${archivedString(it)})" }
            .takeIf { it.isNotEmpty() }
            ?.reduce { a, b -> "$a,\t$b" }
            ?: ""

    private fun archivedString(deal: DealEntity) = if (deal.archived) ", archived" else ""
}
