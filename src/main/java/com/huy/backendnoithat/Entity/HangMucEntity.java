package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;
import lombok.*;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hangMuc", fetch = FetchType.LAZY)
    private List<VatLieuEntity> vatLieuEntity;
    @Setter(AccessLevel.NONE) @Getter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "noi_that_id", referencedColumnName = "id")
    private NoiThatEntity noiThatEntity;

    public void addVatLieu(VatLieuEntity vatLieuEntity) {
        this.vatLieuEntity.add(vatLieuEntity);
    }

}
