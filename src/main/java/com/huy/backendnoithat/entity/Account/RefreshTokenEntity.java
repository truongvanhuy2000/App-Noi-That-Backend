package com.huy.backendnoithat.entity.Account;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Entity
@Table(name = "refresh_token")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "token_id", nullable = false, unique = true)
    private String tokenId;
    @Column(name = "is_valid", nullable = false, unique = true)
    private boolean isValid;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;
    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @JoinColumn(name = "account_id")
    @ManyToOne(targetEntity = AccountEntity.class)
    private AccountEntity accountEntity;
}
