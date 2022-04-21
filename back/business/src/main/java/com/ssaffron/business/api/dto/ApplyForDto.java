package com.ssaffron.business.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyForDto {

    private int applyForIndex;

    private int applyForWashCount;

    private int applyForBeddingCount;

    private int applyForDeliveryCount;

    private int applyForCleaningCount;

    private int applyForShirtCount;

}
