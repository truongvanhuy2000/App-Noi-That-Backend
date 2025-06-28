package com.huy.backendnoithat.model.dto.AccountManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.Account.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonProperty("expiredDate")
    private LocalDate expiredDate;

    public Account(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.username = accountEntity.getUsername();
        this.password = "";
        this.active = accountEntity.isActive();
        this.enabled = accountEntity.isEnabled();
        this.roles = accountEntity.getRoleEntity().stream().map(RoleEntity::getRole).toList();
        this.accountInformation = new AccountInformation(accountEntity.getAccountInformationEntity());
        this.expiredDate = accountEntity.getExpiredDate().toLocalDate();
    }
}
