package com.huy.backendnoithat.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Entity.ThongSo;
import com.huy.backendnoithat.Entity.VatLieu;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class VatLieuResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("thongSo")
    private ThongSo thongSo;
    public VatLieuResponse(VatLieu vatLieu, boolean fetchAll) {
        if (fetchAll) {
            this.thongSo = vatLieu.getThongSo();
        }
        else {
            this.thongSo = null;
        }
        this.id = vatLieu.getId();
        this.name = vatLieu.getName();
    }
}
