package com.mes.aone.dto;

import com.mes.aone.contant.Status;
import com.mes.aone.entity.Vendor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter @Setter
public class SalesOrderDTO {

    private Long salesOrderId;                 // id

    private String productName;                 // 제품명

    private Vendor vendorId;                    // 거래처명

    private Integer salesQty;                   // 수량

    private Date salesDate;                     // 수주일

    private Status salesStatus = Status.A;      // 상태

    private static ModelMapper modelMapper = new ModelMapper();


}
