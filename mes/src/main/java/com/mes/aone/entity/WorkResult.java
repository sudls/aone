package com.mes.aone.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class WorkResult {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workResultId;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrderId;

    @Column(nullable = false)
    private Date workFinishDate;

    @Column(nullable = false)
    private Integer workFinishQty;

}
