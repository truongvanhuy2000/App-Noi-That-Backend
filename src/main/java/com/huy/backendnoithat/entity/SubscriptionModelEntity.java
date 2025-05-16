package com.huy.backendnoithat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscription_model")
public class SubscriptionModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "tier")
    private int tier;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "duration_month")
    private int durationMonth;
    @Column(name = "bonus_month")
    private int bonusMonth;
    @Column(name = "limit_file")
    private int limitFile;
    @Column(name = "price")
    private int price;
}
