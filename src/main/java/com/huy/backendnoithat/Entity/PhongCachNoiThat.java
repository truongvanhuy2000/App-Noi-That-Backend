package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

@Table(name = "phongcachnoithat")
public class PhongCachNoiThat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phongCachNoiThat", fetch = FetchType.LAZY)
    private NoiThat noiThat;
}
