package com.huy.backendnoithat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<X> {
    private long total;
    private X data;

    public static <X> PaginationResponse<X> of(long total, X data) {
        return new PaginationResponse<>(total, data);
    }
}
