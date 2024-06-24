package com.huy.backendnoithat.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "s3file")
public class S3FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
