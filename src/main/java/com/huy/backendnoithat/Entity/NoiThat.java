package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

@Table(name = "noithat")
public class NoiThat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phong_cach_id", referencedColumnName = "id")
    private PhongCachNoiThat phongCachNoiThat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noiThat", fetch = FetchType.LAZY)
    private HangMuc hangMuc;
}
