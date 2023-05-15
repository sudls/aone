package com.mes.aone.entity;

import com.mes.aone.contant.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    @Column(nullable = false)
    @JoinColumn(name = "vendor_id")
    private Vendor vendorId;

    @Column(nullable = false)
    private Integer salesQty;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date salesDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status salesStatus;

    @Column(nullable = false)
    private Date estDelivery;


}
