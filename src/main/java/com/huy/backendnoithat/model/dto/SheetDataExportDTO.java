package com.huy.backendnoithat.model.dto;

import com.huy.backendnoithat.model.enums.ExportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.huytv.model.SheetFileData;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetDataExportDTO {
    private String fileName;
    private Date createdDate;
    private ExportType exportType;
    private SheetFileData exportData;
}
