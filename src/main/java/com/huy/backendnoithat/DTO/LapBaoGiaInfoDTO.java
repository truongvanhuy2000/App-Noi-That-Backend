package com.huy.backendnoithat.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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
    @JsonProperty("id")
    private int id;
    @JsonUnwrapped
    private ThongTinCongTyDTO thongTinCongTy;
    @JsonProperty("note")
    private String note;
}
