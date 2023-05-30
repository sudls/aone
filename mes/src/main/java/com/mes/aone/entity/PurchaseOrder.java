package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name= "purchaseOrder")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseOrderId;            // 발주테이블 고유ID

    @ManyToOne
    @JoinColumn(name = "material_name", referencedColumnName = "material_name")
    private Material materialName;           // 발주 자재명 (BOM이랑 연결??)

    @Column(nullable = false)
    private Integer purchaseQty;             // 수량

    @Column(nullable = false)
    private LocalDateTime purchaseDate;               // 발주 날짜


    @Column(nullable = false)
    private String vendorId;                 // 거래처아이디와 fk

//    @ManyToOne
//    @JoinColumn(name = "vendor_id")
//    private Vendor vendorId;                 // 거래처아이디와 fk

    @Column(nullable = false)
    private LocalDateTime estArrival;    // 예상 입고일자 (발주일자 + 리드타임)
}
