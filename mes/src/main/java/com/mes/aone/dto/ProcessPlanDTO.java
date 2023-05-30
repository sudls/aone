package com.mes.aone.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProcessPlanDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String facilityId;
    private String productName;
    private Long workOrderId;

    public ProcessPlanDTO(LocalDateTime startTime, LocalDateTime endTime, String facilityId, String productName, Long workOrderId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.facilityId = facilityId;
        this.productName = productName;
        this.workOrderId = workOrderId;
    }
}
