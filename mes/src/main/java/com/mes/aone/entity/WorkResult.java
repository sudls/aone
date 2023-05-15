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
    @NotNull
    private Long workResultId;

    @ManyToOne
    @Column
    @NotNull
    private WorkOrder workOrderId;

    @Column
    @NotNull
    private Date workFinishDate;

    @Column
    @NotNull
    private Integer workFinishQty;

}
