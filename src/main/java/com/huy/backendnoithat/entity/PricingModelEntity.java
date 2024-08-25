package com.huy.backendnoithat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pricing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PricingModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Lob
    @Column(name = "pricing_model")
    private String pricingModel;
    @Column(name = "active")
    private boolean active;
}
