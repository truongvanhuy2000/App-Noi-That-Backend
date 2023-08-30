package com.huy.backendnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.ThongSoEntity;
import com.huy.backendnoithat.Entity.VatLieuEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class VatLieu {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("thongSo")
    private ThongSoEntity thongSoEntity;
    public VatLieu(VatLieuEntity vatLieuEntity, boolean fetchAll) {
        if (fetchAll) {
            this.thongSoEntity = vatLieuEntity.getThongSoEntity();
        }
        else {
            this.thongSoEntity = null;
        }
        this.id = vatLieuEntity.getId();
        this.name = vatLieuEntity.getName();
    }
}
