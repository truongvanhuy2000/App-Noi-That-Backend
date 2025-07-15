package com.huy.backendnoithat.entity.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account-restriction")
public class AccountRestrictionEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "expire_date")
    private Long expiredTimestamp;

    @Column(name = "file_limit")
    private Integer fileLimit;

    @JoinColumn(name = "account_id")
    @OneToOne(targetEntity = AccountEntity.class)
    private AccountEntity accountEntity;
}
