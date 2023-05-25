package com.mes.aone.entity;

import com.mes.aone.constant.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name= "facility")
public class Facility {

    @Id
    private String facilityId;

    @Column(length = 50)
    private String facilityName;

    @Column
    private Integer facilityCapacity;

    @Column(nullable = false)
    private Integer facilityReadyTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status facilityStatus;

    @Column(length = 50, nullable = false)
    private String processName;

    public Facility(){}
    public Facility(String facilityId,String facilityName,  Integer facilityCapacity, Integer facilityReadyTime, Status facilityStatus, String processName){
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.facilityCapacity = facilityCapacity;
        this.facilityReadyTime = facilityReadyTime;
        this.facilityStatus = facilityStatus;
        this.processName = processName;
    }
}