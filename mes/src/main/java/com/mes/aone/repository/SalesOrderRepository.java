package com.mes.aone.repository;

import com.mes.aone.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}
