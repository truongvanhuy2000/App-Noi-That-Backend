package com.huy.backendnoithat.DTO.BangNoiThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatLieu {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("thongSo")
    private ThongSo thongSo;
    public VatLieu(VatLieuEntity vatLieuEntity, boolean fetchAll) {
        this.id = vatLieuEntity.getId();
        this.name = vatLieuEntity.getName();
        this.thongSo = null;
        if (fetchAll && vatLieuEntity.getThongSoEntity() != null) {
            this.thongSo = new ThongSo(vatLieuEntity.getThongSoEntity());
        }
    }
}
