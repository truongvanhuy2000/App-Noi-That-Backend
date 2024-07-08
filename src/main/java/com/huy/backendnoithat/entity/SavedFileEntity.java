package com.huy.backendnoithat.entity;

import com.huy.backendnoithat.model.enums.StorageType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;
import java.time.Instant;

@Entity
@Table(name = "saved-file")
public class SavedFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file-name")
    private String fileName;
    @Column(name = "physical-directory")
    private String physicalDirectory;
    @Column(name = "storage-type")
    private StorageType storageType;

    @CreationTimestamp
    @Column(name = "created-date")
    private Date createdDate;
    @UpdateTimestamp
    @Column(name = "updated-date")
    private Date updatedDate;
}
