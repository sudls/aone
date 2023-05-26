package com.mes.aone.dto;

import com.mes.aone.entity.WorkOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class WorkResultDTO {

    private Long workOrderId;
    private String productName;
    private Date workFinishDate;
    private Integer workFinishQty;

    public WorkResultDTO(Long workOrderId, String productName, Date workFinishDate, Integer workFinishQty) {
        this.workOrderId = workOrderId;
        this.productName = productName;
        this.workFinishDate = workFinishDate;
        this.workFinishQty = workFinishQty;
    }

}

