package com.huy.backendnoithat.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    private VatLieu vatLieu;
}
