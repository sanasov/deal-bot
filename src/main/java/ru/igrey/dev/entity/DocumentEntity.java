package ru.igrey.dev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class DocumentEntity {
    private Long id;
    private Long fileId;
    private Long documentTypeId;
    private String filename;
    private Long sourceTokenId;
    private boolean archived;
}
