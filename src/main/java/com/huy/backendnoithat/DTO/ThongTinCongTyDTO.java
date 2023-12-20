package com.huy.backendnoithat.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.InputStream;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ThongTinCongTyDTO {
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
