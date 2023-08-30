package com.huy.backendnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.HangMucEntity;
import com.huy.backendnoithat.Entity.NoiThatEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class NoiThat {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("noiThat")
    private List<HangMucEntity> hangMucEntity;
    public NoiThat(NoiThatEntity noiThatEntity, boolean fetchAll) {
        if (fetchAll) {
            this.hangMucEntity = noiThatEntity.getHangMucEntity();
        }
        else {
            this.hangMucEntity = new ArrayList<>();
        }
        this.id = noiThatEntity.getId();
        this.name = noiThatEntity.getName();
    }
}
