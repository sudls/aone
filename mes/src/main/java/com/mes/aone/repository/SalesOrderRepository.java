package com.mes.aone.repository;

import com.mes.aone.contant.Status;
import com.mes.aone.entity.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

//    @Query(value="select * from salesOrder order by salesOrderId desc")
//    List<SalesOrder> findAllByAllSalesOrderDetail(@Param("salesOrderId") String salesOrderId);

//    @Modifying
//    @Query("select saledsOrderId, productName, salesQty, salesDate, salesStatus) from salesOrder")
//    int updateBoardView(@Param("board_id") Long boardId);

    List<SalesOrder> findAll();


    SalesOrder findBySalesOrderId(Long salesOrderId);

    @Query("SELECT s FROM SalesOrder s WHERE s.salesDate BETWEEN :startDate AND :endDate ")
    Page<SalesOrder> searchAllById(Pageable pageable, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    List<SalesOrder> findByProductName(String productName);
    List<SalesOrder> findByVendorId(String vendorID);

    List<SalesOrder> findBySalesStatus(Status salesStatus );

    List<SalesOrder> findByProductNameAndSalesStatus(String productName, Status salesStatus);

    List<SalesOrder> findByProductNameAndVendorIdAndSalesStatus(String productName, String vendorID, Status salesStatus);

    List<SalesOrder> findByProductNameAndVendorId(String productName, String vendorID);

    List<SalesOrder> findByVendorIdAndSalesStatus(String vendorID,Status salesStatus );


}
