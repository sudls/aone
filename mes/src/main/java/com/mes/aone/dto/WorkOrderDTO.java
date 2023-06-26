package com.mes.aone.dto;

import com.mes.aone.constant.Status;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.WorkOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WorkOrderDTO {

    private Long workOrderId;
    private LocalDateTime workOrderDate;
    private int workOrderQty;
    private Status workStatus;
    private String salesOrder;

    public WorkOrderDTO(Long workOrderId, LocalDateTime workOrderDate, int workOrderQty, Status workStatus,String salesOrder) {
        this.workOrderId = workOrderId;
        this.workOrderDate = workOrderDate;
        this.workOrderQty = workOrderQty;
        this.workStatus = workStatus;
        this.salesOrder = salesOrder;
    }

    // DTO -> 엔티티
    private static ModelMapper modelMapper = new ModelMapper();


    public WorkOrder toWorkOrder(){ return modelMapper.map(this, WorkOrder.class); }


    public static List<WorkOrderDTO> of(List<WorkOrder> workOrderList) {
        List<WorkOrderDTO> workOrderDTOList = new ArrayList<>();
        for (WorkOrder workOrder : workOrderList) {
            WorkOrderDTO workOrderDTO = new WorkOrderDTO(
                    workOrder.getWorkOrderId(),
                    workOrder.getWorkOrderDate(),
                    workOrder.getWorkOrderQty(),
                    workOrder.getWorkStatus(),
                    workOrder.getSalesOrder().getProductName()
            );
            workOrderDTOList.add(workOrderDTO);
        }
        return workOrderDTOList;
    }
}
