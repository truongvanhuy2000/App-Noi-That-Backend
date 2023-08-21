package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "noithat")
@Entity
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
    private List<HangMuc> hangMuc;
    public void addHangMuc(HangMuc hangMuc) {
        this.hangMuc.add(hangMuc);
    }
}
