package com.mes.aone.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class WorkResultDTO {

    private Long workOrderId;
    private String productName;
    private LocalDateTime workFinishDate;
    private Integer workFinishQty;

    public WorkResultDTO(Long workOrderId, String productName, LocalDateTime workFinishDate, Integer workFinishQty) {
        this.workOrderId = workOrderId;
        this.productName = productName;
        this.workFinishDate = workFinishDate;
        this.workFinishQty = workFinishQty;
    }


}

