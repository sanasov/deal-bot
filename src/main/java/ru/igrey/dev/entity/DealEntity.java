package ru.igrey.dev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class DealEntity {
    private Long id;
    private Long authorId;
    private Long dealStatusId;
    private Long mikId;
    private Long officeId;
    private Long typeId;
    private Boolean archived;
}