package ru.igrey.dev.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Cas {
    @SerializedName(value = "casId")
    private Long id;
    private String phone;
    private String firstName;
    private String lastName;
    private List<String> authorities;

    @Override
    public String toString() {
        return "casId: " + id + " phone: " + phone;// + " authorities=" + authorities;
    }
}
