package com.huy.backendnoithat.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.ThongSo;
import com.huy.backendnoithat.Entity.VatLieu;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ThongSoResponse {
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
    public ThongSoResponse(ThongSo thongSo) {
        this.id = thongSo.getId();
        this.dai = thongSo.getDai();
        this.rong = thongSo.getRong();
        this.cao = thongSo.getCao();
        this.donVi = thongSo.getDonVi();
        this.donGia = thongSo.getDonGia();
    }
}
