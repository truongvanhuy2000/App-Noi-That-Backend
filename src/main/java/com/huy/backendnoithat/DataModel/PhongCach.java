package com.huy.backendnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.NoiThatEntity;
import com.huy.backendnoithat.Entity.PhongCachNoiThatEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class PhongCach {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("noiThat")
    private List<NoiThat> noiThatEntity;
    public PhongCach(PhongCachNoiThatEntity phongCachNoiThatEntity, boolean fetchAll) {
        if (fetchAll) {
            this.noiThatEntity = phongCachNoiThatEntity.getNoiThatEntity().stream().map(item -> new NoiThat(item, true)).toList();
        }
        else {
            this.noiThatEntity = new ArrayList<>();
        }
        this.id = phongCachNoiThatEntity.getId();
        this.name = phongCachNoiThatEntity.getName();
    }
}
