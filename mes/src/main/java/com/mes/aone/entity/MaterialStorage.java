package com.mes.aone.entity;

import com.mes.aone.contant.State;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name= "materialStorage")
public class MaterialStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long materialStorageId;   // 자재보관테이블 고유ID

    @ManyToOne
    @JoinColumn(name = "material_name", referencedColumnName = "material_name")
    private Material materialName;        // 자재보관 자재명 발주테이블의 자재명과 연결?

    @Column(nullable = false)
    private Integer materialQty;        // 자재 수량

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State mStorageState;        // 입출고상태

    @Column(nullable = false)
    private Date mStorageDate;          // 입출고 날짜


}
