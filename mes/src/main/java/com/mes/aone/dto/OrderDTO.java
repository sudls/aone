package com.mes.aone.dto;

import com.mes.aone.constant.SalesStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter@Setter
public class OrderDTO {
    private Long salesOrderId; //id
    private String productName; //제품명
    private String vendorId; //거래처 아이디
    private Integer salesQty; //수량
    private LocalDateTime salesDate; //수주일
    private SalesStatus salesStatus; //수주 상태
    private Date estDelivery; // 예상납품일


}
