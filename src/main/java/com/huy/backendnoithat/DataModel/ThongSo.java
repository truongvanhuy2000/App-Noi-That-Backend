package com.huy.backendnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.ThongSoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ThongSo {
    @JsonProperty("id")
    private int id;
    @JsonProperty("dai")
    private float dai;
    @JsonProperty("rong")
    private float rong;
    @JsonProperty("cao")
    private float cao;
    @JsonProperty("donVi")
    private String donVi;
    @JsonProperty("donGia")
    private double donGia;
    public ThongSo(ThongSoEntity thongSoEntity) {
        this.id = thongSoEntity.getId();
        this.dai = thongSoEntity.getDai();
        this.rong = thongSoEntity.getRong();
        this.cao = thongSoEntity.getCao();
        this.donVi = thongSoEntity.getDonVi();
        this.donGia = thongSoEntity.getDonGia();
    }
}
