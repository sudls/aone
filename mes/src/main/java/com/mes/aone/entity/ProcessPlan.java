package com.mes.aone.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name= "processPlan")
public class ProcessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long processPlanId;

//    @ManyToOne
//    @JoinColumn(name = "process_id")
//    private Process processId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

/*    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrderId;*/

    @NotNull
    private String processStage;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facilityId;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

//    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
//    private List<Facility> facilityIdList = new ArrayList<>();
}

