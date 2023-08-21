package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

@Table(name = "vatlieu")
@Entity
public class VatLieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thong_so_id", referencedColumnName = "id")
    private ThongSo thongSo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hang_muc_id", referencedColumnName = "id")
    private HangMuc hangMuc;
}
