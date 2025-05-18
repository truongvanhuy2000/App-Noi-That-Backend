package com.huy.backendnoithat.model.dto;

import com.huy.backendnoithat.model.enums.UploadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedFileDTO {
    private int id;
    private String fileName;
    private UploadStatus uploadStatus;
    private long size;
    private Date updatedDate;
}
