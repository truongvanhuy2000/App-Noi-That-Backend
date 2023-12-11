package com.huy.backendnoithat.DTO;

import com.huy.backendnoithat.Entity.ApplicationInfo;
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
    public ApplicationInfoDTO(ApplicationInfo applicationInfo) {
        id = applicationInfo.getId();
        version = applicationInfo.getVersion();
        changeLog = applicationInfo.getChangeLog();
        updateDate = applicationInfo.getUpdateDate();
    }
}
