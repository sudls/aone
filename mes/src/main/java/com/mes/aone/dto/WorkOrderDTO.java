package com.mes.aone.dto;

import com.mes.aone.constant.Status;
import com.mes.aone.entity.WorkOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class WorkOrderDTO {

    private Long workOrderId;
    private LocalDateTime workOrderDate;
    private int workOrderQty;
    private Status workStatus;
    private String processStage;
    private String productName;


    public WorkOrderDTO(Long workOrderId, LocalDateTime workOrderDate, int workOrderQty, Status workStatus, String processStage, String productName) {
        this.workOrderId = workOrderId;
        this.workOrderDate = workOrderDate;
        this.workOrderQty = workOrderQty;
        this.workStatus = workStatus;
        this.processStage = processStage;
        this.productName = productName;
    }


    public static List<WorkOrderDTO> of(List<WorkOrder> workOrderList) {
        List<WorkOrderDTO> workOrderDTOList = new ArrayList<>();
        for (WorkOrder workOrder : workOrderList) {
            WorkOrderDTO workOrderDTO = new WorkOrderDTO(
                    workOrder.getWorkOrderId(),
                    workOrder.getWorkOrderDate(),
                    workOrder.getWorkOrderQty(),
                    workOrder.getWorkStatus(),
                    workOrder.getProcessPlan().getProcessStage(),
                    workOrder.getSalesOrder().getProductName()
            );
            workOrderDTOList.add(workOrderDTO);
        }
        return workOrderDTOList;
    }

}
