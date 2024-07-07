package com.huy.backendnoithat.Entity.Account;

import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public AccountInformationEntity(AccountInformation accountInformation) {
        this.id = accountInformation.getId();
        this.name = accountInformation.getName();
        this.phone = accountInformation.getPhone();
        this.address = accountInformation.getAddress();
        this.email = accountInformation.getEmail();
        this.gender = accountInformation.getGender();
    }
}
