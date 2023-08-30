package com.huy.backendnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.HangMucEntity;
import com.huy.backendnoithat.Entity.VatLieuEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class HangMuc {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("vatLieu")
    private List<VatLieuEntity> vatLieuEntity;
    public HangMuc(HangMucEntity hangMucEntity, boolean fetchAll) {
        if (fetchAll) {
            this.vatLieuEntity = hangMucEntity.getVatLieuEntity();
        }
        else {
            this.vatLieuEntity = null;
        }
        this.id = hangMucEntity.getId();
        this.name = hangMucEntity.getName();
    }
}
