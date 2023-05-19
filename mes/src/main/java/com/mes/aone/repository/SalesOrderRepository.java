package com.mes.aone.repository;

import com.mes.aone.contant.Status;
import com.mes.aone.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {


    List<SalesOrder> findAll();


    SalesOrder findBySalesOrderId(Long salesOrderId);


    // 제품명, 거래처, 수주상태, 기간 검색
    List<SalesOrder>  findByProductNameAndVendorIdAndSalesStatusAndSalesDateBetween(String productName, String vendorID, Status salesStatus, LocalDateTime startDate, LocalDateTime endDate);

    // 제품명, 거래처, 수주상태 검색
    List<SalesOrder> findByProductNameAndVendorIdAndSalesStatus(String productName, String vendorID, Status salesStatus);

    // 제품명, 거래처, 기간 검색
    List<SalesOrder> findByProductNameAndVendorIdAndSalesDateBetween(String productName, String vendorID, LocalDateTime startDate, LocalDateTime endDate);

    // 제품명, 수주상태, 기간 검색
    List<SalesOrder> findByProductNameAndSalesStatusAndSalesDateBetween(String productName, Status salesStatus, LocalDateTime startDate, LocalDateTime endDate);

    // 제품명, 거래처 검색
    List<SalesOrder> findByProductNameAndVendorId(String productName, String vendorID);

    // 제품명, 수주상태 검색
    List<SalesOrder> findByProductNameAndSalesStatus(String productName, Status salesStatus);

    // 제품명, 기간 검색
    List<SalesOrder> findByProductNameAndSalesDateBetween(String productName, LocalDateTime startDate, LocalDateTime endDate);

    // 거래처명, 수주상태 검색
    List<SalesOrder> findByVendorIdAndSalesStatus(String vendorID,Status salesStatus );

    // 제품명 검색
    List<SalesOrder> findByProductName(String productName);

    // 거래처 검색
    List<SalesOrder> findByVendorId(String vendorID);

    // 수주상태 검색
    List<SalesOrder> findBySalesStatus(Status salesStatus );

    // 기간 검색
    List<SalesOrder> findBySalesDateBetween(LocalDateTime startDate, LocalDateTime endDate);




}
