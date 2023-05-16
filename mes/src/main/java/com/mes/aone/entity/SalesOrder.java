package com.mes.aone.entity;

import com.mes.aone.contant.Status;
import com.mes.aone.dto.SalesOrderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name= "salesOrder")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long salesOrderId;

    @Column(length = 100, nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendorId;

    @Column(nullable = false)
    private Integer salesQty;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date salesDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status salesStatus = Status.A;          // 입력시 기본값 A:대기

    // 예상납품일
//    @Column(nullable = false)
//    private Date estDelivery;


    public static SalesOrder toSalesOrder(SalesOrderDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, SalesOrder.class);
    }

//    public void updateSalesOrder(SalesOrderDTO salesOrderDTO){      //BoardsFormDTO boardsFormDTO
//        this.salesStatus = salesOrderDTO.getsalesStatus();
//    }

}
