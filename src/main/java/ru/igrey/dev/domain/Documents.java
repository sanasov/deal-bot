package ru.igrey.dev.domain;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.igrey.dev.entity.DocumentEntity;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Documents {
    List<DocumentEntity> notAuthorDocuments;
    Integer uploadedDocsCount;

    public List<DocumentEntity> getNotAuthorDocuments() {
        return notAuthorDocuments != null ? notAuthorDocuments : new ArrayList<>();
    }

    @Override
    public String toString() {
        String title = "\nКоличество загруженных доков: " + uploadedDocsCount + "\n"
                + "Сгенерированнные:\n";

        StringBuilder documnentsAsSb = new StringBuilder();
        for (DocumentEntity doc : getNotAuthorDocuments()) {
            documnentsAsSb.append(doc.toString())
                    .append("\n" + StringUtils.repeat("-", 30) + "\n");
        }
        return title + documnentsAsSb.toString();
    }
}
