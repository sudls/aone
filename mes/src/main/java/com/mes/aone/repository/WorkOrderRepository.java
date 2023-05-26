package com.mes.aone.repository;

import com.mes.aone.dto.WorkOrderDTO;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>, QuerydslPredicateExecutor<WorkOrder> {

    //작업지시 조회
    @Query("SELECT NEW com.mes.aone.dto.WorkOrderDTO(w.workOrderId, w.workOrderDate, w.workOrderQty, w.workStatus, " +
            "p.processStage, s.productName) " +
            "FROM WorkOrder w " +
            "JOIN w.processPlan p " +
            "JOIN w.salesOrder s " )
    List<WorkOrderDTO> findWorkOrderDetails();

    
    //작업지시에 따른 수주 정보 조회
    @Query("SELECT s FROM SalesOrder s JOIN WorkOrder w " +
            "ON w.salesOrder.salesOrderId = s.salesOrderId WHERE w.workOrderId = :workOrderId")
    List<SalesOrder> findSalesOrdersByWorkOrderId(@Param("workOrderId") Long workOrderId);



}
