package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.igrey.dev.entity.RisasRequisitionEntity;

import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Deal {
    private final Long id;
    private final Long dealStatusId;
    private final Long mikId;
    private final Long officeId;
    private final Long typeId;
    private final Boolean archived;
    private final Cas author;
    private final String crmTasks;
    private final Documents documents;
    private final List<ServiceDeal> services;
    private final List<RisasRequisitionEntity> risasRequisitions;

    @Override
    public String toString() {
        return header()
                + "Автор(" + (author == null ? "ERROR" : author.toString()) + ")" + "\n"
                + "mikId: " + mikId + ", officeId: " + officeId + "\n"
                + "CRM: " + crmTasks + "\n"
                + "ПЭ: " + risasRequisitions + "\n"
                + "Сервисы: " + services + "\n"
                + documents.toString();
    }

    private String header() {
        String title = "Неизвестный тип сделки";
        String archivedStr = archived ? ", archived" : "";
        if (typeId == 55561) title = "Упрощеная сделка";
        if (typeId == 55562) title = "СПК";
        if (typeId == 55560) title = "Неипотечная сделка";
        if (typeId == 55550) title = "Ипотечная сделка";
        return title + "(№ " + id + ", status: " + dealStatusId + archivedStr + ")" + "\n";

    }

}
