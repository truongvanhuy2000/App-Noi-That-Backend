package com.huy.backendnoithat.entity;

import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.model.enums.StorageType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Entity
@Table(name = "saved_file")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter @Setter
public class SavedFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "physical_name")
    private String physicalName;
    @Column(name = "storage_type")
    private StorageType storageType;
    @Column(name = "is_uploaded")
    private boolean isUploaded;
    @Column(name = "is_backup")
    private boolean isBackup;
    @Column(name = "tag")
    private String tag;
    @Column(name = "checksum")
    private String checksum;
    @Column(name = "expiration")
    private String expiration;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;
    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "is_delete")
    private boolean isDelete;

    @JoinColumn(name = "account_id")
    @ManyToOne(targetEntity = AccountEntity.class)
    private AccountEntity accountEntity;
}
