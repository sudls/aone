package com.mes.aone.entity;

import com.mes.aone.constant.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WorkOrder {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workOrderId;   //수주id

    @Column(nullable = false)
    private LocalDateTime workOrderDate;    // 작업지시일자

    @Column(nullable = false)
    private Integer workOrderQty;   // 작업지시수량

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status workStatus;      //작업진행상태

    @ManyToOne
    @JoinColumn(name = "process_plan_id")
    private ProcessPlan processPlan;    // 공정계획(공정단계확인)

    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrder;      // 수주 - 제품명
}
