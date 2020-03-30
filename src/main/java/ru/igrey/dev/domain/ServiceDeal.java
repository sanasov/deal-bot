package ru.igrey.dev.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ServiceDeal {
    @SerializedName(value = "service_type_id")
    private Long id;

    @Override
    public String toString() {
        return id + "";
    }
}
