package com.huy.backendnoithat.entity.account;

import com.huy.backendnoithat.entity.LapBaoGiaInfoEntity;
import com.huy.backendnoithat.entity.SubscriptionModelEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "active")
    private boolean active;
    @Column(name = "enabled")
    private boolean enabled;

    // No longer used, but kept for backward compatibility
    // TODO: Remove this field in the future
    @Deprecated
    @Column(name = "expire_date")
    private Date expiredDate;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;
    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    private AccountInformationEntity accountInformationEntity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountEntity", fetch = FetchType.LAZY)
    private List<RoleEntity> roleEntity;

    @ManyToMany
    @JoinTable(name = "account_subscription",
        joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    private final List<SubscriptionModelEntity> subscriptions = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accountEntity")
    private AccountRestrictionEntity accountRestrictionEntity;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private LapBaoGiaInfoEntity lapBaoGiaInfoEntity;

    public AccountEntity(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.active = account.getActive() != null ? account.getActive() : false;
        this.enabled = account.getEnabled() != null ? account.getEnabled() : false;
        this.roleEntity = account.getRoles().stream().map(item -> new RoleEntity(0, this, item)).toList();
        this.accountInformationEntity = new AccountInformationEntity(account.getAccountInformation());
    }
}
