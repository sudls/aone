package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity
@Table(name= "material")
public class
Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long materialId;    // 자재테이블 고유ID

    @Column(length = 50, nullable = false, name = "material_name")
    private String materialName; // 자재명

    @Column(nullable = false)
    private Integer materialLeadtime; // 자재별 리드타임(주문준비시간)


    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendorId;         // 거래처 고유ID
}