package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "phongcachnoithat")
@Entity
public class PhongCachNoiThat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phongCachNoiThat", fetch = FetchType.LAZY)
    private List<NoiThat> noiThat;

    public void addNoiThat(NoiThat noiThat) {
        this.noiThat.add(noiThat);
    }
}
