package com.mes.aone.service;

import com.mes.aone.constant.Status;
import com.mes.aone.dto.WorkOrderDTO;
import com.mes.aone.entity.QWorkOrder;
import com.mes.aone.entity.WorkOrder;
import com.mes.aone.repository.SalesOrderRepository;
import com.mes.aone.repository.WorkOrderRepository;
import com.mes.aone.util.MESInfo;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final SalesOrderRepository salesOrderRepository;

    // 작업지시 등록
    public void createWorkOrder(MESInfo mesInfo, Long salesOrderId, String purchaseCheck){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderDate(mesInfo.getSalesDay());
        if (purchaseCheck.equals("enough"))     // 재고가 충분할 때 : 수주량
            workOrder.setWorkOrderQty(mesInfo.getSalesQty());
        else                                    // 완제품 재고가 부족할 때 : 메인 원재료 주문량
            workOrder.setWorkOrderQty(mesInfo.getRequiredBox());
        workOrder.setWorkStatus(Status.A);
        workOrder.setSalesOrder(salesOrderRepository.findBySalesOrderId(salesOrderId));

        workOrderRepository.save(workOrder);
    }

    // 다중검색
    public List<WorkOrder> searchWorkOrders(Long workOrderId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                            String productName, Status workStatus){

        QWorkOrder qWorkOrder = QWorkOrder.workOrder;
        BooleanBuilder builder = new BooleanBuilder();

        if (workOrderId != null) {
            builder.and(qWorkOrder.workOrderId.eq(workOrderId));
        }
        if (startDateTime != null && endDateTime != null) {
            builder.and(qWorkOrder.workOrderDate.between(startDateTime, endDateTime));
        }
        if (productName != null) {
            builder.and(qWorkOrder.salesOrder.productName.eq(productName));
        }
        if (workStatus != null) {
            builder.and(qWorkOrder.workStatus.eq(workStatus));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "workOrderId");
        return (List<WorkOrder>) workOrderRepository.findAll(builder, sort);
    }

}
