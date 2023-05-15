package com.mes.aone.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class WorkOrder {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workOrderId;

    @Column(nullable = false)
    private Date workOrderDate;

    @Column(nullable = false)
    private Integer workOrderQty;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State workStatus;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "process_plan_id")
    private ProcessPlan productionPlanId;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrderId;
}
