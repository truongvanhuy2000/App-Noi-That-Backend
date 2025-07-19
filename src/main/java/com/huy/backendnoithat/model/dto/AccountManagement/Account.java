package com.huy.backendnoithat.model.dto.AccountManagement;

import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.account.AccountRestrictionEntity;
import com.huy.backendnoithat.entity.account.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private int id;
    private String username;
    private String password;
    private Boolean active;
    private Boolean enabled;
    private List<String> roles;
    private AccountInformation accountInformation;
    private LocalDate expiredDate;
    private Integer fileLimit;

    public Account(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.username = accountEntity.getUsername();
        this.password = "";
        this.active = accountEntity.isActive();
        this.enabled = accountEntity.isEnabled();
        this.roles = accountEntity.getRoleEntity().stream().map(RoleEntity::getRole).toList();
        this.accountInformation = new AccountInformation(accountEntity.getAccountInformationEntity());
        if (accountEntity.getAccountRestrictionEntity() != null) {
            AccountRestrictionEntity accountRestrictionEntity = accountEntity.getAccountRestrictionEntity();
            this.fileLimit = accountRestrictionEntity.getFileLimit();
            if (accountRestrictionEntity.getExpiredTimestamp() != null) {
                // Convert the timestamp to LocalDate in the VST timezone, dealing with vietnam's timezone only
                this.expiredDate = LocalDate.ofInstant(Instant.ofEpochMilli(accountRestrictionEntity.getExpiredTimestamp()), ZoneId.of("Asia/Ho_Chi_Minh"));
            }
        }
    }
}
