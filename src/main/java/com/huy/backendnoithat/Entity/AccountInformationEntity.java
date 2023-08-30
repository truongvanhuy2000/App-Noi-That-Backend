package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accountinformation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInformationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="phone")
    private String phone;
    @Column(name="address")
    private String address;
    @Column(name="email")
    private String email;
    @Column(name="gender")
    private String gender;
}
