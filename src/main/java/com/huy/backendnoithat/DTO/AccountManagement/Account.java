package com.huy.backendnoithat.DTO.AccountManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @JsonProperty("id")
    private int id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("roles")
    private List<String> roles;
    @JsonProperty("accountInformation")
    private AccountInformation accountInformation;
}
