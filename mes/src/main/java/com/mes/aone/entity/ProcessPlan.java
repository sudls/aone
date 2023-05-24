package com.mes.aone.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    private Date startTime;

    @NotNull
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    private PurchaseOrder salesOrderId;

    @NotNull
    private Integer processStage;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility_id;

//    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
//    private List<Facility> facilityIdList = new ArrayList<>();
}
