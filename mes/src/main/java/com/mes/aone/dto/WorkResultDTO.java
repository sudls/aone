package com.mes.aone.dto;

import com.mes.aone.entity.WorkResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static List<WorkResultDTO> of(List<WorkResult> workResultList){
        List<WorkResultDTO> workResultDTOList = new ArrayList<>();
        for(WorkResult workResult : workResultList){
            WorkResultDTO workResultDTO = new WorkResultDTO(
                    workResult.getWorkOrder().getWorkOrderId(),
                    workResult.getWorkOrder().getSalesOrder().getProductName(),
                    workResult.getWorkFinishDate(),
                    workResult.getWorkFinishQty()
            );
            workResultDTOList.add(workResultDTO);
        }
            return  workResultDTOList;
    }


}

