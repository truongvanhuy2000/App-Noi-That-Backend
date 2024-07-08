package com.huy.backendnoithat.model.dto;

import com.huy.backendnoithat.entity.ApplicationInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationInfoDTO {
    private int id;
    private String version;
    private String changeLog;
    private LocalDate updateDate;

    public ApplicationInfoDTO(ApplicationInfoEntity applicationInfoEntity) {
        id = applicationInfoEntity.getId();
        version = applicationInfoEntity.getVersion();
        changeLog = applicationInfoEntity.getChangeLog();
        updateDate = applicationInfoEntity.getUpdateDate();
    }
}
