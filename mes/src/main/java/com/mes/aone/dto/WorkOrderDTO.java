package com.mes.aone.dto;

import com.mes.aone.constant.Status;
import com.mes.aone.entity.ProcessPlan;
import com.mes.aone.entity.SalesOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class WorkOrderDTO {

    private Long workOrderId;
    private Date workOrderDate;
    private int workOrderQty;
    private Status workStatus;
    private String productName;

    public WorkOrderDTO(Long workOrderId, Date workOrderDate, int workOrderQty, Status workStatus,String productName) {
        this.workOrderId = workOrderId;
        this.workOrderDate = workOrderDate;
        this.workOrderQty = workOrderQty;
        this.workStatus = workStatus;
        this.productName = productName;
    }

}
