package com.huy.backendnoithat.Entity.BangNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;
import com.huy.backendnoithat.Entity.Account.AccountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "thongso")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThongSoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="dai")
    private Float dai;
    @Column(name="rong")
    private Float rong;
    @Column(name="cao")
    private Float cao;
    @Column(name="don_vi")
    private String donVi;
    @Column(name="don_gia")
    private Double donGia;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "vatlieu_id", referencedColumnName = "id")
    private VatLieuEntity vatLieuEntity;
    public ThongSoEntity(ThongSo thongSo) {
        this.id = thongSo.getId();
        this.dai = thongSo.getDai();
        this.rong = thongSo.getRong();
        this.cao = thongSo.getCao();
        this.donVi = thongSo.getDonVi();
        this.donGia = thongSo.getDonGia();
    }
}
