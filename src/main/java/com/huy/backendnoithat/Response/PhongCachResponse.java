package com.huy.backendnoithat.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.NoiThat;
import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class PhongCachResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("noiThat")
    private List<NoiThat> noiThat;
    public PhongCachResponse(PhongCachNoiThat phongCachNoiThat, boolean fetchAll) {
        if (fetchAll) {
            this.noiThat = phongCachNoiThat.getNoiThat();
        }
        else {
            this.noiThat = new ArrayList<>();
        }
        this.id = phongCachNoiThat.getId();
        this.name = phongCachNoiThat.getName();
    }
}
