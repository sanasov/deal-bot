package ru.igrey.dev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

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

    @Override
    public String toString() {
        String archivedStr = archived ? "(archived) " : "";
        return archivedStr + documentTypeId + ": '" + filename + "' \n"
                + "fileId:" + fileId + ", sourceTokenId:" + sourceTokenId;
    }
}
