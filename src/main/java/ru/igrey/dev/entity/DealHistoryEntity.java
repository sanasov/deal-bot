package ru.igrey.dev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class DealHistoryEntity {
    private Long id;
    private Long dealId;
    private Long userId;
    private Long oldStatusId;
    private Long newStatusId;
    private String comment;
    private String event;
    private Long sourceTokenId;

    public String toString() {
        return "STI:" + sourceTokenId + "; userId: " + userId + ";\n" + oldStatusId + " -->" + event + "--> " + newStatusId;
    }
}