package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "noithat")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoiThat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noiThat", fetch = FetchType.LAZY)
    private List<HangMuc> hangMuc;
    @Setter(AccessLevel.NONE) @Getter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "phong_cach_id", referencedColumnName = "id")
    private PhongCachNoiThat phongCachNoiThat;
    public void addHangMuc(HangMuc hangMuc) {
        this.hangMuc.add(hangMuc);
    }
}
