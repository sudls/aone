package com.mes.aone.repository;

import com.mes.aone.constant.Status;
import com.mes.aone.entity.SalesOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {


    List<SalesOrder> findAll();


    SalesOrder findBySalesOrderId(Long salesOrderId);



    // 제품명, 거래처, 수주상태, 기간 검색
    List<SalesOrder>  findByProductNameAndVendorIdAndSalesStatusAndSalesDateBetween(String productName, String vendorID, Status salesStatus, LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    // 제품명, 거래처, 수주상태 검색
    List<SalesOrder> findByProductNameAndVendorIdAndSalesStatus(String productName, String vendorID, Status salesStatus, Sort sort);

    // 제품명, 거래처, 기간 검색
    List<SalesOrder> findByProductNameAndVendorIdAndSalesDateBetween(String productName, String vendorID, LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    // 제품명, 수주상태, 기간 검색
    List<SalesOrder> findByProductNameAndSalesStatusAndSalesDateBetween(String productName, Status salesStatus, LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    // 제품명, 거래처 검색
    List<SalesOrder> findByProductNameAndVendorId(String productName, String vendorID, Sort sort);

    // 제품명, 수주상태 검색
    List<SalesOrder> findByProductNameAndSalesStatus(String productName, Status salesStatus, Sort sort);

    // 제품명, 기간 검색
    List<SalesOrder> findByProductNameAndSalesDateBetween(String productName, LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    // 거래처명, 수주상태 검색
    List<SalesOrder> findByVendorIdAndSalesStatus(String vendorID,Status salesStatus, Sort sort);

    // 제품명 검색
    List<SalesOrder> findByProductName(String productName, Sort sort);

    // 거래처 검색
    List<SalesOrder> findByVendorId(String vendorID, Sort sort);

    // 수주상태 검색
    List<SalesOrder> findBySalesStatus(Status salesStatus, Sort sort);

    // 기간 검색
    List<SalesOrder> findBySalesDateBetween(LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    //상태가 취소가 아닌것 검색
    List<SalesOrder> findBySalesStatusNot(Status salesStatus);

}
