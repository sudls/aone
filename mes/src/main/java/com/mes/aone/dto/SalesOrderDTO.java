package com.mes.aone.dto;


import com.mes.aone.constant.Status;
import com.mes.aone.entity.SalesOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO {

    private Long salesOrderId;                 // id
    private String productName;                 // 제품명
    private String vendorId;                    // 거래처명
    private Integer salesQty;                   // 수량
    private LocalDateTime salesDate;                     // 수주일
    private Status salesStatus = Status.A;      // 상태
    private LocalDateTime estDelivery;          // 예상납품일

    private static ModelMapper modelMapper = new ModelMapper();


    // dto -> 엔티티
    public SalesOrder createSalesOrder(){
        return modelMapper.map(this, SalesOrder.class);
    }


    public static List<SalesOrderDTO> of(List<SalesOrder> salesOrderList) {
        List<SalesOrderDTO> salesOrderDTOList = new ArrayList<>();
        for (SalesOrder salesOrder : salesOrderList) {
            SalesOrderDTO salesOrderDTO = new SalesOrderDTO(
                    salesOrder.getSalesOrderId(),
                    salesOrder.getProductName(),
                    salesOrder.getVendorId(),
                    salesOrder.getSalesQty(),
                    salesOrder.getSalesDate(),
                    salesOrder.getSalesStatus(),
                    salesOrder.getEstDelivery()
            );
            salesOrderDTOList.add(salesOrderDTO);
        }
        return salesOrderDTOList;
    }


}
