package com.mes.aone.dto;

import com.mes.aone.constant.SalesStatus;
import com.mes.aone.entity.SalesOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter @ToString
public class OrderDTO {
    private Long salesOrderId; //id
    private String productName; //제품명
    @NotBlank
    private String vendorId; //거래처 아이디
    private Integer salesQty; //수량
    private LocalDateTime salesDate; //수주일
    private SalesStatus salesStatus = SalesStatus.A;  //수주 상태
//    private Date estDelivery; // 예상납품일




    private static ModelMapper modelMapper = new ModelMapper();

    // dto -> 엔티티
    public SalesOrder createSalesOrder(){
        return modelMapper.map(this, SalesOrder.class);
    }

}
