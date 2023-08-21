package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "hangmuc")
@Entity
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
    private List<VatLieu> vatLieu;
    public void addVatLieu(VatLieu vatLieu) {
        this.vatLieu.add(vatLieu);
    }
}
