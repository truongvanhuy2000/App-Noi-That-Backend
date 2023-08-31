package com.huy.backendnoithat.DTO.AccountManagement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountInformation {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address")
    private String address;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private String gender;
}
