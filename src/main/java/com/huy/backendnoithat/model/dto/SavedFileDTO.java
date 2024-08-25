package com.huy.backendnoithat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SavedFileDTO {
    private int id;
    private String fileName;
    private boolean isUploaded;
}
