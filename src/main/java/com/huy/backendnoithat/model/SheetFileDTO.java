package com.huy.backendnoithat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class SheetFileDTO implements Serializable {
    private long id;
    private String fileName;
    private byte[] content;
    private long size;
}
