package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "noithat")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoiThatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noiThat", fetch = FetchType.LAZY)
    private List<HangMucEntity> hangMucEntity;
    @Setter(AccessLevel.NONE) @Getter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "phong_cach_id", referencedColumnName = "id")
    private PhongCachNoiThatEntity phongCachNoiThatEntity;
    public void addHangMuc(HangMucEntity hangMucEntity) {
        this.hangMucEntity.add(hangMucEntity);
    }
}
