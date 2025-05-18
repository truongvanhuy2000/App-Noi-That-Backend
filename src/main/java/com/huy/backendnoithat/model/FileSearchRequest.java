package com.huy.backendnoithat.model;

import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.model.enums.UploadStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileSearchRequest {
    private String fileName;
    private FileType fileType;
    private UploadStatus uploadStatus;
    private Integer userId;
}
