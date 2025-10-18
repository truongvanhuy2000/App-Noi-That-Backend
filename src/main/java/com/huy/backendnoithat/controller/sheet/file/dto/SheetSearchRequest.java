package com.huy.backendnoithat.controller.sheet.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetSearchRequest {
    private String fileName;
}
