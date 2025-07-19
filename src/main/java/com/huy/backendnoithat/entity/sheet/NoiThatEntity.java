package com.huy.backendnoithat.entity.sheet;

import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "noithat")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoiThatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "order_index")
    private int orderIndex = 0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noiThatEntity", fetch = FetchType.LAZY)
    private List<HangMucEntity> hangMucEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "phong_cach_id", referencedColumnName = "id")
    private PhongCachNoiThatEntity phongCachNoiThatEntity;

    public NoiThatEntity clone() {
        return NoiThatEntity.builder()
            .name(this.name)
            .orderIndex(this.orderIndex)
            .build();
    }

    public NoiThatEntity(NoiThat noiThat) {
        this.id = noiThat.getId();
        this.name = noiThat.getName();
        this.hangMucEntity = new ArrayList<>();
        if (noiThat.getHangMuc() != null) {
            this.hangMucEntity = noiThat.getHangMuc().stream().map(HangMucEntity::new).toList();
        }
    }

    public void addHangMuc(HangMucEntity hangMucEntity) {
        this.hangMucEntity.add(hangMucEntity);
    }
}
