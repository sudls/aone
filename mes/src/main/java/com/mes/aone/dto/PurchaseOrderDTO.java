package com.mes.aone.dto;

import com.mes.aone.entity.Material;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class PurchaseOrderDTO {
    private Long purchaseOrderId;           // id
    private Material materialName;          // 자재명
    private Integer purchaseQty;            // 수량
    private LocalDateTime purchaseDate;              // 발주일
    private String vendorId;                // 거래처 id
//    private Vendor vendorId;                // 거래처 id
    // private Date estArrival;             // 예상도착일


//    private static ModelMapper modelMapper = new ModelMapper();
//    public PurchaseOrder createPurchaseOrder(){
//        return modelMapper.map(this, PurchaseOrder.class);
//    }
}
