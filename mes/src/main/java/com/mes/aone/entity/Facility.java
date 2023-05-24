package com.mes.aone.entity;

import com.mes.aone.contant.Status;
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

    @Column(length = 50, nullable = false)
    private String facilityName;

//    @Column(nullable = false)
//    private Integer facilityVolume;

    @Column(nullable = false)
    private Integer facilityCapacity;

    @Column(nullable = false)
    private Integer facilityReadyTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status facilityStatus;

    public Facility(){}
    public Facility(String facilityId,String facilityName,  Integer facilityCapacity, Integer facilityReadyTime, Status facilityStatus){
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.facilityCapacity = facilityCapacity;
        this.facilityReadyTime = facilityReadyTime;
        this.facilityStatus = facilityStatus;
    }
}
