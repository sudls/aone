package com.mes.aone.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WorkResult {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workResultId;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @Column(nullable = false)
    private LocalDateTime workFinishDate;

    @Column(nullable = false)
    private Integer workFinishQty;

}
