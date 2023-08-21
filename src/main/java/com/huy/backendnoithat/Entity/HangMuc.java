package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

@Table(name = "hangmuc")
public class HangMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "noi_that_id", referencedColumnName = "id")
    private NoiThat noiThat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hangMuc", fetch = FetchType.LAZY)
    private VatLieu vatLieu;
}
