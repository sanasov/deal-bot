package ru.igrey.dev.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CasUsers {
    private final List<Cas> casUsers;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cas cas : casUsers) {
            sb.append(cas.toString()).append("\n");
        }
        return sb.toString();
    }
}