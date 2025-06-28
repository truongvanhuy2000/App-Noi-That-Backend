package com.huy.backendnoithat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("accountInformation")
    private AccountInformation accountInformation;
    @JsonProperty("subscriptionId")
    private Integer subscriptionId;
}
