package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "thongso")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "thongSo", fetch = FetchType.EAGER)
    private VatLieu vatLieu;
}
