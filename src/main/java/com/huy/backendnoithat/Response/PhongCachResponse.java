package com.huy.backendnoithat.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.NoiThat;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class PhongCachResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("noiThat")
    private List<NoiThat> noiThat;
}
