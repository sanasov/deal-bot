package ru.igrey.dev.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RisasRequisitionEntity {
    private Long requisitionId;
    private String state;

    @Override
    public String toString() {
        return "requisitionId: " + requisitionId + ", state: " + state;
    }
}
