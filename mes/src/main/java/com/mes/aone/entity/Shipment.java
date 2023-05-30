package com.mes.aone.entity;

import com.mes.aone.constant.ShipmentState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime shipmentDate; //출하날짜

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentState shipmentState;  //= ShipmentState.A;

    @OneToOne
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrder;  //수주테이블의 id

    @OneToOne
    @JoinColumn(name = "production_id")
    private Production production;   //생산테이블의 lot번호


}
