package com.huy.backendnoithat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionModelDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer durationMonth;
    private Integer bonusMonth;
    private Integer limitFile;
    private Integer price;
    private Integer tier;
}
