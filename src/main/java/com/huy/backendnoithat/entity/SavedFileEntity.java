package com.huy.backendnoithat.entity;

import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.model.enums.StorageType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Entity
@Table(name = "saved-file")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter @Setter
public class SavedFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file-name")
    private String fileName;
    @Column(name = "physical-name")
    private String physicalName;
    @Column(name = "storage-type")
    private StorageType storageType;
    private boolean isUploaded;

    @CreationTimestamp
    @Column(name = "created-date")
    private Date createdDate;
    @UpdateTimestamp
    @Column(name = "updated-date")
    private Date updatedDate;

    @JoinColumn(name = "account-id")
    @ManyToOne(targetEntity = AccountEntity.class)
    private AccountEntity accountEntity;
}
