package com.huy.backendnoithat.entity;

import com.huy.backendnoithat.model.dto.ApplicationInfoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "appInfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationInfoEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "version")
    private String version;
    @Column(name = "change_log")
    private String changeLog;
    @Column(name = "date")
    private LocalDate updateDate;

    public ApplicationInfoEntity(ApplicationInfoDTO applicationInfoDTO) {
        id = applicationInfoDTO.getId();
        version = applicationInfoDTO.getVersion();
        changeLog = applicationInfoDTO.getChangeLog();
        updateDate = applicationInfoDTO.getUpdateDate();
    }
}
