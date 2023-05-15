package com.mes.aone.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name= "processPlan")
public class ProcessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long processPlanId;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process processId;

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    private PurchaseOrder salesOrderId;

    @NotNull
    private Integer processStage;
}
