package com.mes.aone.repository;

import com.mes.aone.entity.Production;
import com.mes.aone.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
