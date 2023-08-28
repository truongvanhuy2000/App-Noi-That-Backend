package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "vatlieu")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VatLieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "thong_so_id", referencedColumnName = "id")
    private ThongSo thongSo;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hang_muc_id", referencedColumnName = "id")
    private HangMuc hangMuc;
}
