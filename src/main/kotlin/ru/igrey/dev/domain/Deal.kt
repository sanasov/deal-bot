package ru.igrey.dev.domain

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.StringUtils
import ru.igrey.dev.entity.DealHistoryEntity
import ru.igrey.dev.entity.DocumentEntity
import ru.igrey.dev.entity.RisasRequisitionEntity
import java.time.LocalDateTime

data class Deal(
    val id: Long,
    val dealStatusId: Long,
    val mik: Cas? = null,
    val officeId: Long? = null,
    val typeId: Long,
    val archived: Boolean,
    val author: Cas?,
    val crmTasks: String? = null,
    val documents: Documents? = null,
    val services: List<ServiceDeal> = emptyList(),
    val schedules: List<String> = emptyList(),
    val risasRequisitions: List<RisasRequisitionEntity> = emptyList(),
    val lastHistory: List<DealHistoryEntity> = emptyList(),
    val createdTime: LocalDateTime
) {
    override fun toString(): String {
        return (header()
                + "Автор (" + (author ?: "ERROR") + ")\n"
                + "МИК (" + (mik ?: "ERROR") + ", officeId:" + officeId + ")\n"
                + "Запись: " + (if (schedules.isEmpty()) "EMPTY" else schedules) + "\n"
                + "CRM: " + crmTasks + "\n"
                + "ПЭ: " + risasRequisitions + "\n"
                + "Сервисы: " + services + "\n"
                + "История: " + lastHistory + "\n"
                + documents!!.toString())
    }

    private fun header(): String {
        val archivedStr = if (archived) ", archived" else ""
        val title = when (typeId) {
            55561L -> "Упрощеная сделка"
            55562L -> "СПК"
            55560L -> "Неипотечная сделка"
            55550L -> "Ипотечная сделка"
            else -> typeId
        }
        return "$title (№ $id, status: $dealStatusId$archivedStr, $createdTime)\n"
    }
}

data class ServiceDeal(
    @SerializedName(value = "service_type_id")
    private val id: Long,
    private val active: Boolean? = null
) {
    override fun toString() = id.toString() + ""
}

data class Documents(
    val notAuthorDocuments: List<DocumentEntity> = emptyList(),
    val uploadedDocsCount: Int
) {
    override fun toString(): String {
        val title = ("\nКоличество загруженных доков: " + uploadedDocsCount + "\n"
                + "Сгенерированнные:\n")

        val documnentsAsSb = StringBuilder()
        for (doc in notAuthorDocuments) {
            if (doc.archived && doc.documentTypeId == 20580L) continue
            documnentsAsSb.append(doc.toString())
                .append("\n" + StringUtils.repeat("-", 30) + "\n")
        }
        return title + documnentsAsSb.toString()
    }
}

