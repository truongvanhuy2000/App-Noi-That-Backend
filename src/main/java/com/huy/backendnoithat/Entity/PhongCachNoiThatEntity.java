package com.huy.backendnoithat.Entity;

import com.huy.backendnoithat.DataModel.PhongCach;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "phongcachnoithat")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhongCachNoiThatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phongCachNoiThat", fetch = FetchType.LAZY)
    private List<NoiThatEntity> noiThatEntity;
    public PhongCachNoiThatEntity(PhongCach phongCach) {
        this.id = phongCach.getId();
        this.name = phongCach.getName();
        this.noiThatEntity = phongCach.getNoiThatEntity();
    }
    public void addNoiThat(NoiThatEntity noiThatEntity) {
        this.noiThatEntity.add(noiThatEntity);
    }
}
