package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

@Table(name = "thongso")
@Entity
public class ThongSo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="dai")
    private float dai;
    @Column(name="rong")
    private float rong;
    @Column(name="cao")
    private float cao;
    @Column(name="don_vi")
    private String donVi;
    @Column(name="don_gia")
    private double donGia;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "thongSo")
    private VatLieu vatLieu;
}
