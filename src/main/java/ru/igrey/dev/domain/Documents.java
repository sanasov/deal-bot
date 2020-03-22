package ru.igrey.dev.domain;

import lombok.AllArgsConstructor;
import ru.igrey.dev.entity.DocumentEntity;

import java.util.List;

@AllArgsConstructor
public class Documents {
    List<DocumentEntity> notAuthorDocuments;
    Integer uploadedDocsCount;
}
