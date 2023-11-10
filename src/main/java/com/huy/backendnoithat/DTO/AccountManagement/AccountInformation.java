package com.huy.backendnoithat.DTO.AccountManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.Entity.Account.AccountInformationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    public AccountInformation(AccountInformationEntity accountInformationEntity) {
        this.id = accountInformationEntity.getId();
        this.name = accountInformationEntity.getName();
        this.phone = accountInformationEntity.getPhone();
        this.address = accountInformationEntity.getAddress();
        this.email = accountInformationEntity.getEmail();
        this.gender = accountInformationEntity.getGender();
    }
}
