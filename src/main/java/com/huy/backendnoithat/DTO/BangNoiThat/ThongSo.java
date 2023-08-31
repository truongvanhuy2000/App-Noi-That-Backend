package com.huy.backendnoithat.DTO.BangNoiThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.BangNoiThat.ThongSoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongSo {
    @JsonProperty("id")
    private int id;
    @JsonProperty("dai")
    private Float dai;
    @JsonProperty("rong")
    private Float rong;
    @JsonProperty("cao")
    private Float cao;
    @JsonProperty("donVi")
    private String donVi;
    @JsonProperty("donGia")
    private Double donGia;
    public ThongSo(ThongSoEntity thongSoEntity) {
        this.id = thongSoEntity.getId();
        this.dai = thongSoEntity.getDai();
        this.rong = thongSoEntity.getRong();
        this.cao = thongSoEntity.getCao();
        this.donVi = thongSoEntity.getDonVi();
        this.donGia = thongSoEntity.getDonGia();
    }
}
