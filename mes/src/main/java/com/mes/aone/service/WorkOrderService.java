package com.mes.aone.service;

import com.mes.aone.constant.Status;
import com.mes.aone.entity.QWorkOrder;
import com.mes.aone.entity.WorkOrder;
import com.mes.aone.repository.WorkOrderRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Transactional
    public List<WorkOrder> searchWorkOrders(Long workOrderId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                            String processStage, String productName, Status workStatus, Sort sort){

        QWorkOrder qWorkOrder = QWorkOrder.workOrder;
        BooleanBuilder builder = new BooleanBuilder();

        if (workOrderId != null) {
            builder.and(qWorkOrder.workOrderId.eq(workOrderId));
        }
        if (startDateTime != null && endDateTime != null) {
            builder.and(qWorkOrder.workOrderDate.between(startDateTime, endDateTime));
        }
        if (processStage != null) {
            builder.and(qWorkOrder.processPlan.processStage.eq(processStage));
        }
        if (productName != null) {
            builder.and(qWorkOrder.salesOrder.productName.eq(productName));
        }
        if (workStatus != null) {
            builder.and(qWorkOrder.workStatus.eq(workStatus));
        }
        return (List<WorkOrder>) workOrderRepository.findAll(builder);
    }

}
