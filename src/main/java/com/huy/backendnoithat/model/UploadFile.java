package com.huy.backendnoithat.model;

import com.huy.backendnoithat.model.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    private InputStream inputStream;
    private String fileName;
    private String contentType;
    private FileType fileType;
    private long size;
}
