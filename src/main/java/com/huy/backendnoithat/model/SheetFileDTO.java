package com.huy.backendnoithat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SheetFileDTO {
    private long id;
    private String fileName;
    private byte[] content;
    private long size;
}
