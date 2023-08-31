package com.huy.backendnoithat.DTO.BangNoiThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoiThat {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("hangMuc")
    private List<HangMuc> hangMuc;
    public NoiThat(NoiThatEntity noiThatEntity, boolean fetchAll) {
        this.id = noiThatEntity.getId();
        this.name = noiThatEntity.getName();
        this.hangMuc = new ArrayList<>();
        if (fetchAll && noiThatEntity.getHangMucEntity() != null) {
            this.hangMuc = noiThatEntity.getHangMucEntity().stream().map(item -> new HangMuc(item, false)).toList();
        }
    }
}
