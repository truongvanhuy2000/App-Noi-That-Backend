package com.huy.backendnoithat.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LapBaoGiaInfoDTO {
    @JsonProperty("thongTinCongTy")
    private ThongTinCongTyDTO thongTinCongTy;
    @JsonProperty("note")
    private String note;
}
