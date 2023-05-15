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
    @NotNull
    private Long workOrderId;

    @Column
    @NotNull
    private Date workOrderDate;

    @Column
    @NotNull
    private Integer workOrderQty;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private State workStatus;

    @ManyToOne
    @Column
    @NotNull
    private ProcessPlan processPlanId;

    @ManyToOne
    @Column
    @NotNull
    private SalesOrder salesOrderId;
}
