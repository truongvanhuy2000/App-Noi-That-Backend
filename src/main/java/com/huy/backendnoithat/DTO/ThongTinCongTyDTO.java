package com.huy.backendnoithat.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.InputStream;

public class ThongTinCongTyDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("tenCongTy")
    private String tenCongTy;
    @JsonProperty("diaChiVanPhong")
    private String diaChiVanPhong;
    @JsonProperty("soDienThoai")
    private String soDienThoai;
    @JsonProperty("diaChiXuong")
    private String diaChiXuong;
    @JsonProperty("email")
    private String email;
    @JsonProperty("logo")
    private InputStream logo;
}
