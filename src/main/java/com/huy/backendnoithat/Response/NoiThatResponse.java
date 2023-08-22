package com.huy.backendnoithat.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class NoiThatResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("noiThat")
    private List<HangMuc> hangMuc;
    @JsonIgnore
    private PhongCachNoiThat phongCachNoiThat;
}
