package com.huy.backendnoithat.DTO.BangNoiThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HangMuc {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("vatLieu")
    private List<VatLieu> vatLieu;
    public HangMuc(HangMucEntity hangMucEntity, boolean fetchAll) {
        this.id = hangMucEntity.getId();
        this.name = hangMucEntity.getName();
        this.vatLieu = new ArrayList<>();
        if (fetchAll && hangMucEntity.getVatLieuEntity() != null) {
            this.vatLieu = hangMucEntity.getVatLieuEntity().stream().map(item -> new VatLieu(item, false)).toList();
        }
    }
}
