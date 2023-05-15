package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name= "process")
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long processId;

    @Column(length = 50, nullable = false)
    private String processName;

    @Column(nullable = false)
    private Integer processLeadtime;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "facility_id")
    private Facility facilityId;

    @Column(nullable = false)
    private Integer capa;

    @Column(length = 10, nullable = false)
    private String capaUnit;

}
