package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @Override
    public String toString() {
        return header()
                + "Автор(" + author.toString() + ")" + "\n"
                + "mikId: " + mikId + ", officeId: " + officeId + "\n"
                + "CRM: " + crmTasks + "\n"
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
