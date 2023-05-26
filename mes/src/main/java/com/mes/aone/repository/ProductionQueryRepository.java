//package com.mes.aone.repository;
//
//import com.mes.aone.constant.Status;
//import com.mes.aone.entity.ProcessPlan;
//import com.mes.aone.entity.QWorkOrder;
//import com.mes.aone.entity.WorkOrder;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Sort;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDateTime;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Repository
//public class ProductionQueryRepository {
//    private final JPAQueryFactory queryFactory;
//
//    public List<WorkOrder> searchWorkOrders(Long workOrderId, String productName,
//                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
//                                            ProcessPlan processPlan, Status workStatus, Sort sort) {
//        QWorkOrder workOrder = QWorkOrder.workOrder;
//
//        JPAQuery<WorkOrder> query = queryFactory.selectFrom(workOrder)
//                .where(
//                        workOrderId != null ? workOrder.workOrderId.eq(workOrderId) : null,
//                        productName != null ? workOrder.salesOrder.productName.eq(productName) : null,
//                        startDate != null ? workOrder.workOrderDate.goe(startDate) : null,
//                        endDate != null ? workOrder.workOrderDate.loe(endDate) : null,
//                        processPlan != null ? workOrder.processPlan.eq(processPlan) : null,
//                        workStatus != null ? workOrder.workStatus.eq(workStatus) : null
//                );
//        if (sort != null) {
//            query = query.orderBy(sort);
//        }
//        return query.fetch();
//    }
//}
