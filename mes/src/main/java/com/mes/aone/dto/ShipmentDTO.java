package com.mes.aone.dto;

import com.mes.aone.constant.ShipmentState;
import com.mes.aone.entity.Shipment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShipmentDTO {


    private Long salesOrderId; //수주id
    private String vendorId; //거래처(salesorder에서)
    private String shipmentProduct; //제품명
    private int shipmentQty;    //제품수량
    private LocalDateTime shipmentDate; //출하날짜
    private ShipmentState shipmentState;

    public ShipmentDTO(Long salesOrderId, String vendorId, String shipmentProduct, int shipmentQty, LocalDateTime shipmentDate, ShipmentState shipmentState){
        this.salesOrderId = salesOrderId;
        this.vendorId = vendorId;
        this.shipmentProduct=shipmentProduct;
        this.shipmentQty=shipmentQty;
        this.shipmentDate=shipmentDate;
        this.shipmentState=shipmentState;
    }

    public static List<ShipmentDTO> of(List<Shipment> shipmentList) {
        List<ShipmentDTO> shipmentDTOList = new ArrayList<>();
        for (Shipment shipment : shipmentList) {
            ShipmentDTO shipmentDTO = new ShipmentDTO(
                    shipment.getSalesOrder().getSalesOrderId(),
                    shipment.getSalesOrder().getVendorId(),
                    shipment.getShipmentProduct(),
                    shipment.getShipmentQty(),
                    shipment.getShipmentDate(),
                    shipment.getShipmentState()
            );
            shipmentDTOList.add(shipmentDTO);
        }
        return shipmentDTOList;
    }

}
