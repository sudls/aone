package com.mes.aone.entity;

import com.mes.aone.constant.MaterialState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name= "materialStorage")
public class MaterialStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialStorageId;   // 자재보관테이블 고유ID

    @ManyToOne
    @JoinColumn(name = "material_name", referencedColumnName = "material_name")
    private Material materialName;        // 자재보관 자재명 발주테이블의 자재명과 연결?

    @Column(nullable = false)
    private Integer materialQty;        // 자재 수량

    @Column(nullable = false)
    private String unit;

    @Column(name = "material_storage_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialState materialStorageState;        // 입출고상태

    @Column(name = "material_storage_date", nullable = false)
    private LocalDateTime materialStorageDate;          // 입출고 날짜


}
