package com.huy.backendnoithat.entity.sheet;

import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "hangmuc")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HangMucEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "order_index")
    private int orderIndex = 0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hangMucEntity", fetch = FetchType.LAZY)
    private List<VatLieuEntity> vatLieuEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "noi_that_id", referencedColumnName = "id")
    private NoiThatEntity noiThatEntity;

    public HangMucEntity clone() {
        return HangMucEntity.builder()
            .name(this.name)
            .orderIndex(this.orderIndex)
            .build();
    }

    public HangMucEntity(HangMuc hangMuc) {
        this.id = hangMuc.getId();
        this.name = hangMuc.getName();
        this.vatLieuEntity = new ArrayList<>();
        if (hangMuc.getVatLieu() != null) {
            this.vatLieuEntity = hangMuc.getVatLieu().stream().map(VatLieuEntity::new).toList();
        }
    }
}
