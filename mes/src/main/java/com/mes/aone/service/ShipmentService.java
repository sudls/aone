package com.mes.aone.service;

import com.mes.aone.constant.ShipmentState;
import com.mes.aone.entity.QShipment;
import com.mes.aone.entity.Shipment;
import com.mes.aone.repository.ShipmentRepository;
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
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    // 다중검색
    public List<Shipment> searchShipment(Long salesOrderId, String vendorId, String shipmentProduct, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                         String shipmentState){

        QShipment qShipment = QShipment.shipment;
        BooleanBuilder builder = new BooleanBuilder();

        if (salesOrderId != null) {
            builder.and(qShipment.salesOrder.salesOrderId.eq(salesOrderId));
        }
        if (vendorId != null) {
            builder.and(qShipment.salesOrder.vendorId.eq(vendorId));
        }
        if (shipmentProduct != null) {
            builder.and(qShipment.shipmentProduct.eq(shipmentProduct));
        }
        if (startDateTime != null && endDateTime != null) {
            builder.and(qShipment.shipmentDate.between(startDateTime, endDateTime));
        }
        if (shipmentState != null) {
            builder.and(qShipment.shipmentState.eq(ShipmentState.valueOf(shipmentState)));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "salesOrder.salesOrderId");
        return (List<Shipment>) shipmentRepository.findAll(builder, sort);
    }

}
