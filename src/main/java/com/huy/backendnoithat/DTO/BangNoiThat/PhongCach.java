package com.huy.backendnoithat.DTO.BangNoiThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhongCach {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("noiThat")
    private List<NoiThat> noiThat;
    // Copy constructor
    public PhongCach(PhongCachNoiThatEntity phongCachNoiThatEntity, boolean fetchAll) {
        this.id = phongCachNoiThatEntity.getId();
        this.name = phongCachNoiThatEntity.getName();
        this.noiThat = new ArrayList<>();
        if (fetchAll && phongCachNoiThatEntity.getNoiThatEntity() != null) {
            this.noiThat = phongCachNoiThatEntity.getNoiThatEntity().stream().map(item -> new NoiThat(item, false)).toList();
        }
    }
}
