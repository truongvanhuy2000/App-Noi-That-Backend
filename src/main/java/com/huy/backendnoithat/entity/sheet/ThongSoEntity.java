package com.huy.backendnoithat.entity.sheet;

import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.ThongSo;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "thongso")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThongSoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "dai")
    private Float dai;
    @Column(name = "rong")
    private Float rong;
    @Column(name = "cao")
    private Float cao;
    @Column(name = "don_vi")
    private String donVi;
    @Column(name = "don_gia")
    private Double donGia;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public static ThongSoEntity blank() {
        ThongSoEntity thongSo = new ThongSoEntity();
        thongSo.setId(0);
        thongSo.setDai(0f);
        thongSo.setRong(0f);
        thongSo.setCao(0f);
        thongSo.setDonVi("");
        thongSo.setDonGia(0d);
        return thongSo;
    }

    public ThongSoEntity clone() {
        return ThongSoEntity.builder()
            .dai(this.dai)
            .rong(this.rong)
            .cao(this.cao)
            .donVi(this.donVi)
            .donGia(this.donGia)
            .build();
    }
}
