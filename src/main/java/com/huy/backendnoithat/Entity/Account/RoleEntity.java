package com.huy.backendnoithat.Entity.Account;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id", referencedColumnName = "id")
    @Setter(AccessLevel.NONE) @Getter(AccessLevel.NONE)
    private AccountEntity accountEntity;
    @Column(name="role")
    private String role;
}
