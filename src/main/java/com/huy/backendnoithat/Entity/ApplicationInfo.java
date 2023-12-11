package com.huy.backendnoithat.Entity;

import com.huy.backendnoithat.DTO.ApplicationInfoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class ApplicationInfo {
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
    public ApplicationInfo(ApplicationInfoDTO applicationInfoDTO) {
        id = applicationInfoDTO.getId();
        version = applicationInfoDTO.getVersion();
        changeLog = applicationInfoDTO.getChangeLog();
        updateDate = applicationInfoDTO.getUpdateDate();
    }
}
