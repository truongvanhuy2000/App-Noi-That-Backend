package com.huy.backendnoithat.DTO;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.micrometer.common.util.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.*;

import java.io.InputStream;
import java.util.Base64;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ThongTinCongTyDTO {
    @JsonProperty("logo")
    private byte[] logo;
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
    @JsonProperty("createdDate")
    Date createdDate;
    @JsonGetter("logo")
    public String getBase64Logo() {
        if (logo == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(logo);
    }
    @JsonSetter("logo")
    public void setBase64Logo(String base64Logo) {
        if (StringUtils.isBlank(base64Logo)) {
            return;
        }
        this.logo = Base64.getDecoder().decode(base64Logo);
    }
}
