package com.huy.backendnoithat.Entity.BangNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.Entity.Account.AccountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "hangmuc")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class HangMucEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hangMucEntity", fetch = FetchType.LAZY)
    private List<VatLieuEntity> vatLieuEntity;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "noi_that_id", referencedColumnName = "id")
    private NoiThatEntity noiThatEntity;
    public HangMucEntity(HangMuc hangMuc) {
        this.id = hangMuc.getId();
        this.name = hangMuc.getName();
        this.vatLieuEntity = new ArrayList<>();
        if (hangMuc.getVatLieu() != null) {
            this.vatLieuEntity = hangMuc.getVatLieu().stream().map(VatLieuEntity::new).toList();
        }
    }
    public void addVatLieu(VatLieuEntity vatLieuEntity) {
        this.vatLieuEntity.add(vatLieuEntity);
    }

}
