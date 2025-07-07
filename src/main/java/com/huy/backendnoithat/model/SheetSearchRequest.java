package com.huy.backendnoithat.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetSearchRequest {
    private String fileName;
}
