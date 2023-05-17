package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Shipment {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shipmentId;    //출하id

    @Column(nullable = false)
    private String shipmentProduct; //제품명

    @Column(nullable = false)
    private int shipmentQty;    //제품수량

    @Column(nullable = false)
    private LocalDate shipmentDate; //출하날짜

    @OneToOne
    @JoinColumn(name = "salesOrderId")
    private SalesOrder salesOrder;  //수주테이블의 id

    @OneToOne
    @JoinColumn(name = "lotNumber")
    private Production production;   //생산테이블의 lot번호


}