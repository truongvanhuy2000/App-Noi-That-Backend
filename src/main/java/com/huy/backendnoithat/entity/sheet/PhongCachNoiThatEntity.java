package com.huy.backendnoithat.entity.sheet;

import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "phongcachnoithat")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhongCachNoiThatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "order_index")
    private int orderIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phongCachNoiThatEntity", fetch = FetchType.LAZY)
    private List<NoiThatEntity> noiThatEntity;

    public PhongCachNoiThatEntity clone() {
        return PhongCachNoiThatEntity.builder()
            .name(this.name)
            .orderIndex(this.orderIndex)
            .build();
    }

    public PhongCachNoiThatEntity(PhongCach phongCach) {
        this.id = phongCach.getId();
        this.name = phongCach.getName();
        this.noiThatEntity = new ArrayList<>();
        if (phongCach.getNoiThat() != null) {
            this.noiThatEntity = phongCach.getNoiThat().stream().map(NoiThatEntity::new).toList();
        }
    }
}
