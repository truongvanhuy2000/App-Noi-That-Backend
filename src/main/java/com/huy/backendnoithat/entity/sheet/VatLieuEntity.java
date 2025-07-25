package com.huy.backendnoithat.entity.sheet;

import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "vatlieu")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatLieuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "order_index")
    private int orderIndex = 0;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vatLieuEntity", fetch = FetchType.LAZY)
    private ThongSoEntity thongSoEntity;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "hang_muc_id", referencedColumnName = "id")
    private HangMucEntity hangMucEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    public VatLieuEntity clone() {
        return VatLieuEntity.builder()
            .name(this.name)
            .orderIndex(this.orderIndex)
            .build();
    }

    public VatLieuEntity(VatLieu vatLieu) {
        this.id = vatLieu.getId();
        this.name = vatLieu.getName();
        this.thongSoEntity = new ThongSoEntity();
        if (vatLieu.getThongSo() != null) {
            this.thongSoEntity = new ThongSoEntity(vatLieu.getThongSo());
        }
    }
}
