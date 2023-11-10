package com.huy.backendnoithat.Entity.BangNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.Entity.Account.AccountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "vatlieu")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VatLieuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vatLieuEntity" ,fetch = FetchType.LAZY)
    private ThongSoEntity thongSoEntity;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "hang_muc_id", referencedColumnName = "id")
    private HangMucEntity hangMucEntity;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;
    public VatLieuEntity(VatLieu vatLieu) {
        this.id = vatLieu.getId();
        this.name = vatLieu.getName();
        this.thongSoEntity = new ThongSoEntity();
        if (vatLieu.getThongSo() != null){
            this.thongSoEntity = new ThongSoEntity(vatLieu.getThongSo());
        }
    }
}
