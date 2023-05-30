package com.mes.aone.entity;

import com.mes.aone.constant.Status;
import com.mes.aone.dto.OrderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(length = 10, nullable = false)
    private String vendorId;

    @Column(nullable = false)
    private Integer salesQty;

    @Column(nullable = false)
    private LocalDateTime salesDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status salesStatus = Status.A;          // 입력시 기본값 A:대기


//     예상납품일
    @Column(nullable = false)
    private LocalDateTime estDelivery;





    private static ModelMapper modelMapper = new ModelMapper();

    // DTO -> 엔티티
    public static SalesOrder toSalesOrder(OrderDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, SalesOrder.class);
    }



}
