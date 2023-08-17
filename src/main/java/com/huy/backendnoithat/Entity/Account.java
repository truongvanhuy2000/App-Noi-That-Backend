package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "Account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="active")
    private boolean active;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    private AccountInformation accountInformation;
    @Column(name="enabled")
    private boolean enabled;
}
