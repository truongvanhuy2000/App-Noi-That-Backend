package com.huy.backendnoithat.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.NoiThat;
import com.huy.backendnoithat.Entity.VatLieu;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class HangMucResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("vatLieu")
    private List<VatLieu> vatLieu;
    @JsonIgnore
    private NoiThat noiThat;
}
