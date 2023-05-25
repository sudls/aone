package com.mes.aone.entity;

import com.mes.aone.constant.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class WorkOrder {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workOrderId;   //수주

    @Column(nullable = false)
    private Date workOrderDate;

    @Column(nullable = false)
    private Integer workOrderQty;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status workStatus;

    @ManyToOne
    @JoinColumn(name = "process_plan_id")
    private ProcessPlan processPlan;

    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrder;
}
