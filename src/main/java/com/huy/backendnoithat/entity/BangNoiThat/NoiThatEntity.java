package com.huy.backendnoithat.entity.BangNoiThat;

import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "noithat")
@Entity
@Getter
@Setter
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
    private int orderIndex;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noiThatEntity", fetch = FetchType.LAZY)
    private List<HangMucEntity> hangMucEntity;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "phong_cach_id", referencedColumnName = "id")
    private PhongCachNoiThatEntity phongCachNoiThatEntity;

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
