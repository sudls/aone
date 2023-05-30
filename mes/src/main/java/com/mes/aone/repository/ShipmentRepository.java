package com.mes.aone.repository;

import com.mes.aone.dto.ShipmentDTO;
import com.mes.aone.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>, QuerydslPredicateExecutor<Shipment> {

    //출하현황조회
    @Query("SELECT NEW com.mes.aone.dto.ShipmentDTO(sh.salesOrder.salesOrderId, sh.salesOrder.vendorId, sh.shipmentProduct, sh.shipmentQty, sh.shipmentDate, sh.shipmentState )"+
    "FROM Shipment sh JOIN sh.salesOrder s" + " ORDER BY sh.salesOrder.salesOrderId DESC")
    List<ShipmentDTO> findShipmentDetails();
}
