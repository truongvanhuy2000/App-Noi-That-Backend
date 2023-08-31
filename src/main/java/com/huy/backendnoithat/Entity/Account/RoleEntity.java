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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="username", referencedColumnName = "username")
    @Setter(AccessLevel.NONE) @Getter(AccessLevel.NONE)
    private AccountEntity accountEntity;

    @Column(name="role")
    private String role;
}
